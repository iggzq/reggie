package com.mystudy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mystudy.reggie.dto.DishDto;
import com.mystudy.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品，同时增加口味，需要操作两张表
    public void saveDishFlavor(DishDto dishDto);

    //根据id查询菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateDishFlavor(DishDto dishDto);


}
