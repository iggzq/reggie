package com.mystudy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mystudy.reggie.common.R;
import com.mystudy.reggie.dto.SetmealDto;
import com.mystudy.reggie.entity.Category;
import com.mystudy.reggie.entity.Setmeal;
import com.mystudy.reggie.entity.SetmealDish;
import com.mystudy.reggie.service.CategoryService;
import com.mystudy.reggie.service.SetmealService;
import com.mystudy.reggie.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> list(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo, lambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);


        return R.success(setmealDtoPage);
    }

    /**
     * 删除套餐
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {

        setmealService.removeWithDish(ids);
        return R.success("删除成功");

    }

    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable Integer status, Long[] ids) {
        //构造条件构造器
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        //添加过滤条件
        updateWrapper.set(Setmeal::getStatus, status).in(Setmeal::getId, ids);
        setmealService.update(updateWrapper);

        return R.success("修改成功");
    }

    /**
     * 根据条件查询套餐数据
     *
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        String redisName = String.valueOf(setmeal.getId());
        List<Setmeal> list;
        list = (List<Setmeal>) redisTemplate.opsForValue().get(redisName);
        if (list != null) {
            return R.success(list);
        }
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(setmeal.getId() != null, Setmeal::getCategoryId, setmeal.getId());
        lambdaQueryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        list = setmealService.list(lambdaQueryWrapper);

        redisTemplate.opsForValue().set(redisName, list);

        return R.success(list);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> get(@PathVariable Long id) {
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId, id);
        Setmeal one = setmealService.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(one, setmealDto);
        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(list);


        return R.success(setmealDto);
    }
}
