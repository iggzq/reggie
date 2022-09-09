package com.mystudy.reggie.service.serivceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystudy.reggie.entity.Category;
import com.mystudy.reggie.mapper.CategoryMapper;
import com.mystudy.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
