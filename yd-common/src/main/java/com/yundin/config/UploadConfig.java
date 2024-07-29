package com.yundin.config;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
@org.springframework.context.annotation.Configuration
public class UploadConfig {

    @Value("${yd.qiniu.accesskey}")
    private String accessKey;
    @Value("${yd.qiniu.secretkey}")
    private String secretKey;

    @Bean
    public Auth getAuth(){
        return Auth.create(accessKey,secretKey);
    }

    @Bean
    public UploadManager getUploadManager(){
        return new UploadManager(new Configuration());
    }
}