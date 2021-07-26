package com.promo.gmall.manager.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.promo.gmall.model.properties.AliyunOssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Component
public class OssManager {

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private AliyunOssProperties aliyunOssProperties;


    public String upload(String path, ByteArrayInputStream inputStream) {
        String bucketName = aliyunOssProperties.getBucketName();
        PutObjectResult result = ossClient.putObject(bucketName, path, inputStream);


        //创建文件路径
        //  String fileUrl = path +"/"+(dateStr + "/" + UUID.randomUUID().toString().replace("-","")+"-"+file.getName());

        return result.getETag();
    }


    public void delete(String path) {


    }


}
