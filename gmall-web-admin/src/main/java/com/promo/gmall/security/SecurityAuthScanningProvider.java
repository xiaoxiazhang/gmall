package com.promo.gmall.security;

import com.google.common.collect.Lists;
import com.promo.gmall.model.acl.SecurityAuthNodeBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Component
public class SecurityAuthScanningProvider implements ApplicationContextAware {

    /**
     * 缓存认证节点数据
     */
    private volatile List<SecurityAuthNodeBO> cachedAuthNodeBOList = new ArrayList<>();


    /**
     * 资源模式
     */
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /**
     * 使用spring security 权限配置对应controller包
     */
    public static final String SECURITY_BASE_PACKAGE = "com.promo.gmall.controller";


    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();


    private ApplicationContext applicationContext;


    @Autowired
    private Environment environment;


    public List<SecurityAuthNodeBO> scan() {

        // 如果已经扫描过，就直接使用缓存扫描的数据
        if (!cachedAuthNodeBOList.isEmpty()) {
            return cachedAuthNodeBOList;
        }

        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(SECURITY_BASE_PACKAGE) + '/' + DEFAULT_RESOURCE_PATTERN;
        Resource[] resources;
        try {

            resources = applicationContext.getResources(packageSearchPath);

            List<SecurityAuthNodeBO> result = Lists.newArrayList();

            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
                MergedAnnotations classAnnotations = annotationMetadata.getAnnotations();

                // 父路径对应2级菜单
                String parentPath = getRequestPath(classAnnotations);
                String menuDescription = getAnnotationValue(classAnnotations, Api.class);

                Set<MethodMetadata> allAuthorizeMethods = annotationMetadata.getAnnotatedMethods(PreAuthorize.class.getName());

                // 存在认证方法节点添加menu节点
                if (!CollectionUtils.isEmpty(allAuthorizeMethods)) {
                    result.add(SecurityAuthNodeBO.createMenuNode(handlePath(parentPath), menuDescription));
                }

                allAuthorizeMethods.forEach(methodMetadata -> {
                    MergedAnnotations methodAnnotations = methodMetadata.getAnnotations();

                    String methodPath = getRequestPath(methodAnnotations);
                    String permUrl = buildPermUrl(parentPath, methodPath);
                    String description = getAnnotationValue(methodAnnotations, ApiOperation.class);
                    String permission = getPermissionFromPreAuthorize(methodAnnotations);

                    // 添加权限叶子节点
                    result.add(SecurityAuthNodeBO.createLeafNode(permission, permUrl, description));
                });
            }

            // 返回并缓存扫描数据
            return cachedAuthNodeBOList = result;
        } catch (IOException ex) {
            throw new IllegalStateException("I/O failure during classpath scanning", ex);
        }
    }


    /**
     * 去除URL开头的 '/' 字符
     */
    private String handlePath(String path) {
        if (path.startsWith("/")) {
            return path.substring(1);
        }
        return path;
    }


    /**
     * 创建权限URL
     */
    private String buildPermUrl(String menuPath, String methodPath) {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(menuPath)) {
            if (!menuPath.startsWith("/")) {
                sb.append("/");
            }
            sb.append(menuPath);
        }

        if (StringUtils.isNotEmpty(methodPath)) {
            if (!methodPath.startsWith("/")) {
                sb.append("/");
            }
            sb.append(methodPath);
        }

        return sb.toString();
    }


    /**
     * 获取注解value属性对应的值
     */
    private <T extends Annotation> String getAnnotationValue(MergedAnnotations annotations, Class<T> clazz) {
        MergedAnnotation<T> mergedAnnotation = annotations.get(clazz);
        if (mergedAnnotation.isPresent()) {
            return mergedAnnotation.getString("value");
        }

        return StringUtils.EMPTY;
    }


    /**
     * 获取@PreAuthorize中的配置中的权限值
     * <p>
     * 例如："hasAuthority('user@save')" ==> user@save
     */
    private String getPermissionFromPreAuthorize(MergedAnnotations annotations) {
        MergedAnnotation<PreAuthorize> preAuthorizeMergedAnnotation = annotations.get(PreAuthorize.class);

        if (preAuthorizeMergedAnnotation.isPresent()) {
            String authValue = preAuthorizeMergedAnnotation.getString("value");

            if (StringUtils.isEmpty(authValue)) {
                return StringUtils.EMPTY;
            }

            int first = authValue.indexOf("'");
            int last = authValue.lastIndexOf("'");
            if (first == -1 || last == -1 || first == last) {
                return StringUtils.EMPTY;
            }

            return authValue.substring((first + 1), last);
        }

        return StringUtils.EMPTY;
    }


    /**
     * 获取方法对应的路径配置
     */
    public String getRequestPath(MergedAnnotations annotations) {
        MergedAnnotation<RequestMapping> requestMappingMergedAnnotation = annotations.get(RequestMapping.class);
        if (requestMappingMergedAnnotation.isPresent()) {
            String[] values = requestMappingMergedAnnotation.getStringArray("value");
            return values.length == 0 ? StringUtils.EMPTY : values[0];
        }

        MergedAnnotation<GetMapping> getMappingMergedAnnotation = annotations.get(GetMapping.class);
        if (getMappingMergedAnnotation.isPresent()) {
            String[] values = getMappingMergedAnnotation.getStringArray("value");
            return values.length == 0 ? StringUtils.EMPTY : values[0];
        }

        MergedAnnotation<PostMapping> postMappingMergedAnnotation = annotations.get(PostMapping.class);
        if (postMappingMergedAnnotation.isPresent()) {
            String[] values = postMappingMergedAnnotation.getStringArray("value");
            return values.length == 0 ? StringUtils.EMPTY : values[0];
        }

        MergedAnnotation<PutMapping> putMappingMergedAnnotation = annotations.get(PutMapping.class);
        if (putMappingMergedAnnotation.isPresent()) {
            String[] values = putMappingMergedAnnotation.getStringArray("value");
            return values.length == 0 ? StringUtils.EMPTY : values[0];
        }

        MergedAnnotation<DeleteMapping> deleteMappingMergedAnnotation = annotations.get(DeleteMapping.class);
        if (deleteMappingMergedAnnotation.isPresent()) {
            String[] values = deleteMappingMergedAnnotation.getStringArray("value");
            return values.length == 0 ? StringUtils.EMPTY : values[0];
        }

        return StringUtils.EMPTY;
    }


    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(environment.resolveRequiredPlaceholders(basePackage));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


}
