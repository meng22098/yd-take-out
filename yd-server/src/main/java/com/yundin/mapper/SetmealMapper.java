package com.yundin.mapper;

import com.github.pagehelper.Page;
import com.yundin.annotation.AutoFill;
import com.yundin.dto.SetmealPageQueryDTO;
import com.yundin.entity.Setmeal;
import com.yundin.entity.SetmealDish;
import com.yundin.enumeration.OperationType;
import com.yundin.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetmealMapper {
    Page<SetmealVO> pageQery(SetmealPageQueryDTO setmealPageQueryDTO);
    @AutoFill(OperationType.INSERT)
    void save(Setmeal setmeal);
}
