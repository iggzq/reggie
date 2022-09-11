package com.mystudy.reggie.service.serivceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystudy.reggie.common.CustomException;
import com.mystudy.reggie.entity.Category;
import com.mystudy.reggie.entity.Dish;
import com.mystudy.reggie.entity.Setmeal;
import com.mystudy.reggie.mapper.CategoryMapper;
import com.mystudy.reggie.service.CategoryService;
import com.mystudy.reggie.service.DishService;
import com.mystudy.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetMealService setMealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(lambdaQueryWrapper);
        if (count1 > 0) {
            throw new CustomException("当前分类项关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId, id);
        int count2 = setMealService.count(lambdaQueryWrapper1);
        if (count2 > 0){
            throw new CustomException("当前分类项关联了套餐，不能删除");
        }

        super.removeById(id);
    }
}
