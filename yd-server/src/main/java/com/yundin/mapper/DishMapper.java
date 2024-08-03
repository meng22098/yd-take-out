package com.yundin.mapper;

import com.github.pagehelper.Page;
import com.yundin.annotation.AutoFill;
import com.yundin.dto.DishPageQueryDTO;
import com.yundin.entity.Dish;
import com.yundin.enumeration.OperationType;
import com.yundin.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper
{
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);
    @AutoFill(OperationType.INSERT)
    void save(Dish dish);

    @Select("select d.*,c.name as categoryName  from dish as d left join category as c on d.category_id=c.id where d.id=#{id} order by d.create_time desc")
    Dish getById(Integer id);
    @Select("select *  from dish where category_id=#{categoryId}")
    List<DishVO> list(Integer categoryId);
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);
    List<DishVO> list1(Integer id);

    Integer countByMap(Map map);
}
