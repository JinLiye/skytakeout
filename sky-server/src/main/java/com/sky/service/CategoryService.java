package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 分类查询
     * @param dto
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO dto);

    void startOrstop(Integer status, Long id);

    void editCatagory(CategoryDTO categoryDTO);

    void add(CategoryDTO categoryDTO);

    void deleteById(Long id);

    List<Category> list(Integer type);
}
