package com.yundin.service;

import com.yundin.dto.DishDTO;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Dish;
import com.yundin.result.PageResult;

public interface DishService {
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void save(DishDTO dishDTO);
}
