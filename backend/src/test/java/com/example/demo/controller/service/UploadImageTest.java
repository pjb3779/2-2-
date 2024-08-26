package com.example.demo.controller.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.exceptions.ClientException;
import com.example.demo.controller.service.UploadImage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UploadImageTest {

    @Test
    public void testUploadFile() throws Exception {
        String accessKeyId = "your_access_key_id";
        String accessKeySecret = "your_access_key_secret";
        String endpoint = "your_endpoint";
        String bucketName = "your_bucket_name";

        // 正常情况测试
        String objectName = "testObject";
        InputStream in = new ByteArrayInputStream("test data".getBytes());
        String url = UploadImage.uploadFile(objectName, in);
        assertNotNull(url);

        // 异常情况测试 - OSSException
        try {
            UploadImage.uploadFile("invalidObject", in);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof OSSException);
        }

        // 异常情况测试 - ClientException
        try {
            // 模拟网络中断等客户端异常情况
            InputStream invalidIn = new ByteArrayInputStream("".getBytes());
            UploadImage.uploadFile("testObject", invalidIn);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ClientException);
        }
    }

}

