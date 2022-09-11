package com.mystudy.reggie.controller;

import com.mystudy.reggie.common.R;
import com.mystudy.reggie.dto.SetmealDto;
import com.mystudy.reggie.entity.SetmealDish;
import com.mystudy.reggie.service.SetMealService;
import com.mystudy.reggie.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetMealService setMealService;
    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(SetmealDto setmealDto){
        setMealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }
}
