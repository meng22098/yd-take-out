package com.yundin.controller.admin;

import com.yundin.dto.DishDTO;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Dish;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.DishService;
import com.yundin.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishColtroller {
    @Autowired
    DishService dishService; @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 清理缓存数据
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }


    @GetMapping("/page")
    @ApiOperation("菜品分页")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO)
    {
        log.info("菜品分页:{}",dishPageQueryDTO);
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增菜品")
    @CacheEvict(cacheNames = "dish_",key = "#dishDTO.getCategoryId")//key: setmealCache::100
    public Result save(@RequestBody DishDTO dishDTO)
    {
        log.info("新增菜品:{}",dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable("id") Integer id)
    {
        log.info("根据id查询菜品:{}",id);
        DishVO dishVO=dishService.getById(id);
        return Result.success(dishVO);
    }
    @GetMapping("list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<DishVO>> list(Integer categoryId)
    {
        log.info("根据分类id查询菜品:{}",categoryId);
        List<DishVO> list=dishService.list(categoryId);
        return Result.success(list);
    }
    @PutMapping
    @ApiOperation("修改菜品")
    @CacheEvict(cacheNames = "dish_",allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO)
    {
        log.info("修改菜品:{}",dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    @CacheEvict(cacheNames = "dish_",allEntries = true)
    public Result delete(@RequestParam   List<Long> ids)
    {
        log.info("批量删除菜品:{}",ids);
        dishService.delete(ids);
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售、停售")
    @CacheEvict(cacheNames = "dish_",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id)
    {
        log.info("菜品起售、停售:{},{}",status,id);
        dishService.startOrStop(status,id);
        return Result.success();
    }
}
