package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.constant.MessageConstant;
import com.yundin.constant.StatusConstant;
import com.yundin.dto.DishDTO;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Category;
import com.yundin.entity.Dish;
import com.yundin.entity.DishFlavor;
import com.yundin.exception.DeletionNotAllowedException;
import com.yundin.mapper.DishFlavorMapper;
import com.yundin.mapper.DishMapper;
import com.yundin.mapper.SetmealDishMapper;
import com.yundin.result.PageResult;
import com.yundin.service.DishService;
import com.yundin.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;
    /**
     *分页搜索
     *
     * @param dishPageQueryDTO
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        if (dishPageQueryDTO.getName()!=null||dishPageQueryDTO.getStatus()!=null||dishPageQueryDTO.getCategoryId()!=null)
        {
            dishPageQueryDTO.setPage(1);
        }
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageQuery(dishPageQueryDTO);
        long total=page.getTotal();
        List<DishVO> list=page.getResult();
        return new PageResult(total,list);
    }
    /**
     *添加菜单
     *
     * @param dishDTO
     */
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
    /**
     *根据id查询菜品
     *
     * @param id
     */
    @Override
    public DishVO getById(Integer id) {
       Dish dish= dishMapper.getById(id);
       List<DishFlavor> dishFlavors=dishFlavorMapper.getById(id);
       DishVO dishVO = new DishVO();
       BeanUtils.copyProperties(dish, dishVO);
       dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     *根据分类id查询菜品
     *
     * @param categoryId
     */
    @Override
    public List<Dish> list(Integer categoryId) {
        return dishMapper.list(categoryId);
    }
    /**
     * 菜品修改
     *
     * @param dishDTO
     */
    @Transactional//事务
    @Override
    public void update(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        //删除相关口味
        dishFlavorMapper.delete(dish.getId());
        List<DishFlavor> dishFlavor=dishDTO.getFlavors();
        Long dishId = dish.getId();
        if (dishFlavor != null && dishFlavor.size() > 0) {
            dishFlavor.forEach(Flavor -> {
                Flavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(dishFlavor);
        }
    }
    /**
     * 菜品批量删除
     *
     * @param ids
     */
    @Transactional//事务
    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(Math.toIntExact(id));
            if (dish.getStatus() == StatusConstant.ENABLE) {
                //当前菜品处于起售中，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否能够删除---是否被套餐关联了？？
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            //当前菜品被套餐关联了，不能删除
            throw new
                    DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品表中的菜品数据
        for (Long id : ids) {
            dishMapper.deleteById(id);//后绪步骤实现
            //删除菜品关联的口味数据
            dishFlavorMapper.deleteByDishId(id);//后绪步骤实现
        }
    }
    /**
     * 菜品起售、停售
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish=new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.update(dish);
    }
}
