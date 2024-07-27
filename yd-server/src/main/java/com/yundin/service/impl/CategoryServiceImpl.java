package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.dto.CategoryPageQueryDTO;
import com.yundin.entity.Category;
import com.yundin.entity.Employee;
import com.yundin.mapper.CategoryMapper;
import com.yundin.result.PageResult;
import com.yundin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page=categoryMapper.pageQuery(categoryPageQueryDTO);
        //查询条数
        long total = page.getTotal();
        //结果数据
        List<Category> records = page.getResult();
        return new PageResult(total,records);
    }
}
