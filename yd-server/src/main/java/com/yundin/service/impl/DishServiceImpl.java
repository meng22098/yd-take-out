package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.dto.DishDTO;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Dish;
import com.yundin.entity.DishFlavor;
import com.yundin.mapper.DishFlavorMapper;
import com.yundin.mapper.DishMapper;
import com.yundin.result.PageResult;
import com.yundin.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<Map> page=dishMapper.pageQuery(dishPageQueryDTO);
        long total=page.getTotal();
        List<Map> list=page.getResult();
        return new PageResult(total,list);
    }
    @Override
    public void save(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.save(dish);
        List<DishFlavor> dishFlavor=dishDTO.getFlavors();
        Long dishId = dish.getId();
        if (dishFlavor != null && dishFlavor.size() > 0) {
            dishFlavor.forEach(Flavor -> {
                Flavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(dishFlavor);//后绪步骤实现
        }
    }
}
