package com.yundin.controller.user;

import com.yundin.dto.ShoppingCartDTO;
import com.yundin.entity.ShoppingCart;
import com.yundin.result.Result;
import com.yundin.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api("购物车接口")
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @RequestMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO)
    {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);//后绪步骤实现
        return Result.success();
    }

    @RequestMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list()
    {
        log.info("查看购物车");
        List<ShoppingCart> list=shoppingCartService.list();
        return Result.success(list);
    }
}
