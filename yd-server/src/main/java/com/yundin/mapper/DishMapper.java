package com.yundin.mapper;

import com.github.pagehelper.Page;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface DishMapper
{
    Page<Map> pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
