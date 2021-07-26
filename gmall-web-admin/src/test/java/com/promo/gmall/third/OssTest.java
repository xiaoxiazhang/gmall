package com.promo.gmall.third;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class OssTest {


    @Test
    public void testUpload() {

        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "LTAI4G2HkkgUZBW3zx2PDANS";
        String accessKeySecret = "c7tfZ5e1q8SqHcoE1TMCyUC2tmILne";
        String bucketName = "cinda-promo";
        String objectName = "hello/hello.txt";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String content = "Hello OSS";
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));

        ossClient.shutdown();
    }


    @Test
    public void delete() {


    }


}
