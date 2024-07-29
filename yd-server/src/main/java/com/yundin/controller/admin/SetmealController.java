package com.yundin.controller.admin;

import com.yundin.dto.SetmealDTO;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.entity.SetmealDish;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.SetmealService;
import com.yundin.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO)
    {
        log.info("新增套餐:{}",setmealDTO);
        setmealService.save(setmealDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable("id") Integer id)
    {
        log.info("根据id查询套餐:{}",id);
        SetmealVO setmealVO= setmealService.getById(id);
        return Result.success(setmealVO);
    }

}
