package com.mystudy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mystudy.reggie.entity.Category;
import org.springframework.stereotype.Service;


public interface CategoryService extends IService<Category> {

    public void remove(Long id);
}
