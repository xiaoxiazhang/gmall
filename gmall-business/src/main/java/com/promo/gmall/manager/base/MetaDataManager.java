package com.promo.gmall.manager.base;

import com.promo.gmall.domain.MetaDataDO;
import com.promo.gmall.enums.IsDeletedEnum;
import com.promo.gmall.mapper.MetaDataMapper;
import com.promo.gmall.model.base.MetaDataBO;
import com.promo.gmall.redis.CacheHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.promo.gmall.constants.CacheConstants.*;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Component
public class MetaDataManager {

    @Resource
    private CacheHelper cacheHelper;

    @Resource
    private MetaDataMapper metaDataMapper;


    public void save(MetaDataBO metaDataBO) {
        MetaDataDO metaDataDO = metaDataBO.convert2DO();
        metaDataMapper.insertSelective(metaDataDO);

        // 失效对应缓存
        cacheHelper.delete(String.format(META_TYPE_KEY, metaDataBO.getType()));
    }


    public void update(MetaDataBO metaDataBO) {
        MetaDataDO metaDataDO = metaDataBO.convert2DO();
        metaDataMapper.updateByPrimaryKeySelective(metaDataDO);

        // 失效对应缓存
        cacheHelper.delete(String.format(META_TYPE_KEY, metaDataBO.getType()));
    }


    /**
     * 通过ID查询元数据
     */
    public MetaDataBO getById(Long id) {
        MetaDataDO metaDataDO = metaDataMapper.selectByPrimaryKey(id);
        return MetaDataBO.from(metaDataDO);
    }


    /**
     * 逻辑删除指定
     */
    public void logicDelete(Long id) {
        MetaDataDO metaDataDO = new MetaDataDO();
        metaDataDO.setId(id);
        metaDataDO.setIsDeleted(IsDeletedEnum.YES.getCode());

        metaDataMapper.updateByPrimaryKeySelective(metaDataDO);
    }


    /**
     * 通过类型查找对应的配置项
     */
    public List<MetaDataBO> listByType(Integer type) {
        return cacheHelper.getOrCreate(String.format(META_TYPE_KEY, type), () -> {
            List<MetaDataDO> metaDataDOList = metaDataMapper.listByType(type);
            return metaDataDOList.stream()
                    .map(MetaDataBO::from)
                    .collect(Collectors.toList());
        }, TWO_HOURS);
    }
}
