package com.yundin.mapper;

import com.github.pagehelper.Page;
import com.yundin.dto.CategoryPageQueryDTO;
import com.yundin.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    public Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void save(Category category);

    void update(Category category);
    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
