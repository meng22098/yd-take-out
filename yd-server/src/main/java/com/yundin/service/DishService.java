package com.yundin.service;

import com.yundin.dto.DishDTO;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Dish;
import com.yundin.result.PageResult;
import com.yundin.vo.DishVO;

import java.util.List;

public interface DishService {
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void save(DishDTO dishDTO);

    DishVO getById(Integer id);

    List<Dish> list(Integer categoryId);

    void update(DishDTO dishDTO);

    void delete(List<Long> ids);

    void startOrStop(Integer status, Long id);
}
