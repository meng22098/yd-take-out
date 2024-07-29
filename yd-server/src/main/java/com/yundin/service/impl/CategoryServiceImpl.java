package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.constant.StatusConstant;
import com.yundin.context.BaseContext;
import com.yundin.dto.CategoryDTO;
import com.yundin.dto.CategoryPageQueryDTO;
import com.yundin.entity.Category;
import com.yundin.entity.Employee;
import com.yundin.mapper.CategoryMapper;
import com.yundin.result.PageResult;
import com.yundin.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    /**
     * 分页查询
     *
     * @param categoryPageQueryDTO
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        if (categoryPageQueryDTO.getName()!=null||categoryPageQueryDTO.getType()!=null)
        {
            categoryPageQueryDTO.setPage(1);
        }
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page=categoryMapper.pageQuery(categoryPageQueryDTO);
        //查询条数
        long total = page.getTotal();
        //结果数据
        List<Category> records = page.getResult();
        return new PageResult(total,records);
    }
    /**
     * 添加分类
     *
     * @param categoryDTO
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.ENABLE);
        categoryMapper.save(category);
    }
    /**
     * 修改分类
     *
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category=new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        categoryMapper.update(category);
    }
    /**
     * 删除分类
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
    /**
     * 根据类型查询分类
     *
     * @param type
     */
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
    /**
     *启用、禁用分类
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status,Long id) {
        Category category=new Category();
        category.setStatus(status);
        category.setId(id);
        categoryMapper.update(category);
    }
}
