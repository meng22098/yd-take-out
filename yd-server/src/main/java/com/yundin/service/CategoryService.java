package com.yundin.service;

import com.yundin.dto.CategoryDTO;
import com.yundin.dto.CategoryPageQueryDTO;
import com.yundin.entity.Category;
import com.yundin.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void save(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(Integer id);

    List<Category> list(Integer type);

    void startOrStop(Integer status, Long id);
}
