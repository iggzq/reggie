package com.mystudy.reggie.service.serivceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystudy.reggie.entity.OrderDetail;
import com.mystudy.reggie.mapper.OrderDetailMapper;
import com.mystudy.reggie.service.OrderDetailService;
import com.mystudy.reggie.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
