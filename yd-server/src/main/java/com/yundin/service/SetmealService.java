package com.yundin.service;

import com.yundin.dto.SetmealDTO;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.result.PageResult;
import com.yundin.vo.SetmealVO;

public interface SetmealService {
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void save(SetmealDTO setmealDTO);

    SetmealVO getById(Integer id);

    void update(SetmealDTO setmealDTO);
}
