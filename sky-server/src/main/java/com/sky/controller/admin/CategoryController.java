package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.*;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.EmployeeService;
import com.sky.utils.AliOssUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 分类管理
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private JwtProperties jwtProperties;

    private static final Logger log = LoggerFactory.getLogger(AliOssUtil.class);


    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询，蚕食为{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<CategoryDTO>> list(Integer type) {
        List<Category> list = categoryService.list(type);
        return Result.success();
    }

    @PostMapping("status/{status}")
    @ApiOperation("启用禁用分类")
    public Result startOrstop(@PathVariable("status") Integer status, Long id) {
        log.info("启用禁用分类");
        categoryService.startOrstop(status,id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改分类信息")
    public Result editCatefory(@RequestBody CategoryDTO categoryDTO) {
        log.info("进入编辑分类信息");
        categoryService.editCatagory(categoryDTO);
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类");
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除分类")
    public Result<String> deleteCategory(Long id) {
        log.info("删除分类");
        categoryService.deleteById(id);
        return Result.success();
    }



}
