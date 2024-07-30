package com.yundin.controller.user;

import com.yundin.dto.SetmealDTO;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.entity.Dish;
import com.yundin.entity.Setmeal;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.DishService;
import com.yundin.service.SetmealService;
import com.yundin.vo.DishVO;
import com.yundin.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api("套餐相关接口")
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    @Autowired
    DishService dishService;
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    public Result<List<Setmeal>> getById(Integer categoryId)
    {
        log.info("根据id查询套餐:{}",categoryId);
        List<Setmeal> setmeals=setmealService.list(categoryId);
        return Result.success(setmeals);
    }
    @GetMapping("/dish/{id}")
    @ApiOperation("根据套餐id查询包含的菜品")
    public Result<List<DishVO>> list(@PathVariable Integer id)
    {
        log.info("根据套餐id查询包含的菜品:{}",id);
        List<DishVO> list=dishService.list1(id);
        return Result.success(list);
    }
}
