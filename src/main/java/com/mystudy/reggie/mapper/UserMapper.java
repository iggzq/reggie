package com.mystudy.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystudy.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
