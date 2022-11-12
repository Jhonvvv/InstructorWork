package com.clm.common.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author su
 * @Date 2021/12/26 22:12
 */
@Component
public class ALiYunOSS implements InitializingBean {

    @Value(value = "${clm.oss.endpoint}")
    private  String endpoint;

    @Value(value = "${clm.oss.accessKey}")
    private String accessKey;

    @Value(value = "${clm.oss.secretKey}")
    private  String secretKey;

    @Value(value = "${clm.oss.bucketName}")
    private  String bucketName;

    public static String ENDPOINT;
    public static String ACCESS_KEY;
    public static String SECRET_KEY;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT=endpoint;
        ACCESS_KEY=accessKey;
        SECRET_KEY=secretKey;
        BUCKET_NAME=bucketName;
    }
}
