package com.yundin.controller.admin;

import com.yundin.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api("店铺操作")
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    RedisTemplate redisTemplate;
    @PutMapping("/{status}")
    @ApiModelProperty("设置营业状态")
    public Result setStatus(@PathVariable Integer status)
    {
        log.info("设置营业状态:{}",status==1?"营业中":"打样中");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }
    @RequestMapping("/status")
    @ApiModelProperty("获取营业状态")
    public Result getStatus()
    {
        Integer status= (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("获取营业状态:{}",status==1?"营业中":"打样中");
        return Result.success(status);
    }

}
