package com.yundin.service;

import com.yundin.dto.SetmealDTO;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.entity.Setmeal;
import com.yundin.result.PageResult;
import com.yundin.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    void save(SetmealDTO setmealDTO);

    SetmealVO getById(Integer id);

    void update(SetmealDTO setmealDTO);

    void startOrStop(Integer status, Long id);

    void delete(List<Long> ids);

    List<Setmeal> list(Integer categoryId);
}
