package com.mystudy.reggie.service.serivceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystudy.reggie.dto.SetmealDto;
import com.mystudy.reggie.entity.Setmeal;
import com.mystudy.reggie.entity.SetmealDish;
import com.mystudy.reggie.mapper.SetMealMapper;
import com.mystudy.reggie.service.SetMealService;
import com.mystudy.reggie.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes = setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }
}
