package com.yundin.controller.admin;

import com.yundin.dto.SetmealDTO;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api("套餐相关接口")
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    @RequestMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO)
    {
        log.info("分页查询:{}",setmealPageQueryDTO);//日志
        PageResult pageResult=setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
}
