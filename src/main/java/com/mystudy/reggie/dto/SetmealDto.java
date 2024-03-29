package com.mystudy.reggie.dto;

import com.mystudy.reggie.entity.Setmeal;
import com.mystudy.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
