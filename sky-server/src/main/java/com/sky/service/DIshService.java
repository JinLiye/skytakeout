package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;

public interface DIshService {
    /**
     * 新增菜品和对应的口味数据
     * @param dishdto
     */
    public void saveWithFlavor(DishDTO dishdto);
}
