package com.yundin.service;

import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.result.PageResult;

public interface SetmealService {
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);
}
