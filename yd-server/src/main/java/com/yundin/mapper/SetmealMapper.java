package com.yundin.mapper;

import com.github.pagehelper.Page;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {
    Page<SetmealVO> pageQery(SetmealPageQueryDTO setmealPageQueryDTO);
}
