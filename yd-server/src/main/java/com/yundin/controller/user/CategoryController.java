package com.yundin.controller.user;

import com.yundin.dto.CategoryDTO;
import com.yundin.dto.CategoryPageQueryDTO;
import com.yundin.entity.Category;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public  Result<List<Category>> list(Integer type)
    {
        log.info("根据类型查询分类:{}",type);
        List<Category> list=categoryService.list(type);
        return Result.success(list);
    }
}
