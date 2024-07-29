package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.dto.SetmealDTO;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.entity.Setmeal;
import com.yundin.entity.SetmealDish;
import com.yundin.mapper.SetmealDishMapper;
import com.yundin.mapper.SetmealMapper;
import com.yundin.result.PageResult;
import com.yundin.service.SetmealService;
import com.yundin.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    SetmealDishMapper setmealDishMapper;
    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        if (setmealPageQueryDTO.getName()!=null||setmealPageQueryDTO.getCategoryId()!=null||setmealPageQueryDTO.getStatus()!=null)
        {
            setmealPageQueryDTO.setPage(1);
        }
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page=setmealMapper.pageQery(setmealPageQueryDTO);
        long total=page.getTotal();
        List<SetmealVO> records=page.getResult();
        return new PageResult(total,records);
    }

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional//事务
    @Override
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.save(setmeal);
        List<SetmealDish> list=setmealDTO.getSetmealDishes();
        long setmealDishId=setmeal.getId();
        if (list!=null||list.size()>0)
        {
            list.forEach(Flavor -> {
                Flavor.setSetmealId(setmealDishId);
            });
            setmealDishMapper.insertBatch(list);
        }
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Integer id) {
        Setmeal setmeal=setmealMapper.getById(id);
        List<SetmealDish> setmealDishes=setmealDishMapper.getById(id);
        SetmealVO setmealVO=new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Transactional//事务
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        Long setmealId = setmeal.getId();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishes.forEach(Flavor -> {
                Flavor.setSetmealId(setmealId);
            });
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }
}
