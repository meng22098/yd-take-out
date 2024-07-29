package com.yundin.utils;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class QiniuUtils {

    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private Auth auth;

    @Value("${yd.qiniu.bucketname}")
    private String bucketName;
    @Value("${yd.qiniu.ossPath}")
    private String url;

    public String upload(InputStream file, String fileName) {
        try {
            String token = auth.uploadToken(bucketName);
            Response res = uploadManager.put(file, fileName, token, null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛云出错:" + res);
            }
            return "http://"+url+"/"+fileName;
        }catch (Exception ex){
            throw new RuntimeException("上传七牛云出错:" + ex.getMessage());
        }

    }
}