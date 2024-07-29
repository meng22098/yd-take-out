package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.mapper.SetmealMapper;
import com.yundin.result.PageResult;
import com.yundin.service.SetmealService;
import com.yundin.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    SetmealMapper setmealMapper;
    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page=setmealMapper.pageQery(setmealPageQueryDTO);
        long total=page.getTotal();
        List<SetmealVO> records=page.getResult();
        return new PageResult(total,records);
    }
}
