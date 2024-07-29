package com.yundin.service;

import com.yundin.dto.SetmealDTO;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.result.PageResult;

public interface SetmealService {
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void save(SetmealDTO setmealDTO);
}
