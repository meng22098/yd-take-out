package com.yundin.controller.admin;
import com.yundin.constant.MessageConstant;
import com.yundin.result.Result;
import com.yundin.utils.QiniuUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private QiniuUtils qiniuOssUtil;
    /**
     * 文件上传
     * @param file
     * @return */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
         try {
             //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀 dfdfdf.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String filePath = qiniuOssUtil.upload(file.getInputStream(),objectName);
            return Result.success(filePath);
         } catch (IOException e) {
            log.error("文件上传失败：{}", e);
         }
         return Result.error(MessageConstant.UPLOAD_FAILED);
     }
}

