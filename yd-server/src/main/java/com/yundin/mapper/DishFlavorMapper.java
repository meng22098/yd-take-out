package com.yundin.mapper;

import com.yundin.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    void insertBatch(List<DishFlavor> dishFlavor);
    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> getById(Integer id);
    @Delete("delete from dish_flavor where dish_id=#{id}")
    void delete(Long id);
}
