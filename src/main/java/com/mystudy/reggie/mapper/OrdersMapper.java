package com.mystudy.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystudy.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    //用户下单
}
