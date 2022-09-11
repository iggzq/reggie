package com.mystudy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mystudy.reggie.dto.SetmealDto;
import com.mystudy.reggie.entity.Setmeal;

public interface SetMealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);
}
