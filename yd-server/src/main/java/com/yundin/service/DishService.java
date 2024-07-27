package com.yundin.service;

import com.yundin.dto.DishPageQueryDTO;
import com.yundin.result.PageResult;

public interface DishService {
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
