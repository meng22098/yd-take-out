package com.yundin.controller.admin;

import com.yundin.dto.CategoryDTO;
import com.yundin.dto.CategoryPageQueryDTO;
import com.yundin.entity.Category;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO)
    {
        log.info("分页查询：{}",categoryPageQueryDTO);
        PageResult pageResult=categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("新增分类:{}",categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }
    @PutMapping
    @ApiOperation("修改分类")
    public Result update(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("修改分类:{}",categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result delete(Integer id)
    {
        log.info("根据id删除分类:{}",id);
        categoryService.delete(id);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public  Result<List<Category>> list(Integer type)
    {
        log.info("根据类型查询分类:{}",type);
        List<Category> list=categoryService.list(type);
        return Result.success(list);
    }
    @PostMapping("/status/{status}")
    @ApiOperation("启用、禁用分类")
    public Result startOrStop(@PathVariable("status") Integer status,Long id)
    {
        log.info("启用、禁用分类:{},{}",status,id);
        categoryService.startOrStop(status,id);
        return Result.success();
    }
}
