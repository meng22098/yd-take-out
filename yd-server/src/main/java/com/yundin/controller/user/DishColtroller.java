package com.yundin.controller.user;

import com.yundin.dto.DishDTO;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Dish;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.DishService;
import com.yundin.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController("userDishColtroller")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishColtroller {
    @Autowired
    DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("list")
    @ApiOperation("根据分类id查询菜品")
    @Cacheable(cacheNames = "dish_",key="#categoryId")
    public Result<List<DishVO>> list(Integer categoryId)
    {
        log.info("根据分类id查询菜品:{}",categoryId);
        List<DishVO> list=dishService.list(categoryId);
        return Result.success(list);
    }
}
