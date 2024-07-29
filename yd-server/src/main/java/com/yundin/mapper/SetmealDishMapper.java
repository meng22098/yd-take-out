package com.yundin.mapper;

import com.yundin.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    void insertBatch(List<SetmealDish> list);
    @Select("select * from setmeal_dish where setmeal_id=#{id}")
    List<SetmealDish> getById(Integer id);
    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void deleteBy(Long id);
}
