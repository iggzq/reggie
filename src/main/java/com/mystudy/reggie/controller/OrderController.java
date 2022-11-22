package com.mystudy.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mystudy.reggie.common.BaseContext;
import com.mystudy.reggie.common.R;
import com.mystudy.reggie.dto.OrdersDto;
import com.mystudy.reggie.entity.Dish;
import com.mystudy.reggie.entity.OrderDetail;
import com.mystudy.reggie.entity.Orders;
import com.mystudy.reggie.service.OrderDetailService;
import com.mystudy.reggie.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        ordersService.submit(orders);
        return R.success("提交成功");
    }

    @GetMapping("/page")
    public R<Page<Orders>> page(int page, int pageSize, String number, String beginTime, String endTime) {
        Page<Orders> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (number != null) {
            lambdaQueryWrapper.like(Orders::getNumber, number);
        }
        if (beginTime != null && endTime != null) {
            lambdaQueryWrapper.ge(Orders::getOrderTime, beginTime);
            lambdaQueryWrapper.le(Orders::getOrderTime, endTime);
        }
        ordersService.page(page1, lambdaQueryWrapper);
        return R.success(page1);
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page, int pageSize) {
        Page<Orders> page1 = new Page<>(page, pageSize);
        Page<OrdersDto> page2 = new Page<>();
        Long currentId = BaseContext.getCurrentId();

//        ordersService.page(page1,lambdaQueryWrapper);

        LambdaQueryWrapper<Orders> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Orders::getUserId, currentId);
        ordersService.page(page1, lambdaQueryWrapper1);
        BeanUtils.copyProperties(page1, page2, "records");

        List<Orders> records = page1.getRecords();
        List<OrdersDto> ordersDtos = records.stream().map((item) ->{
            OrdersDto ordersDto = new OrdersDto();
            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OrderDetail::getOrderId,item.getNumber());
            List<OrderDetail> list = orderDetailService.list(lambdaQueryWrapper);
            ordersDto.setOrderDetails(list);
            return ordersDto;
        }).collect(Collectors.toList());

//        LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        for (Orders orders : list) {
//            BeanUtils.copyProperties(orders,ordersDto);
//            String number = orders.getNumber();
//            lambdaQueryWrapper.eq(OrderDetail::getOrderId,number);
//            List<OrderDetail> list1 = orderDetailService.list(lambdaQueryWrapper);
//            ordersDto.setOrderDetails(list1);
//            ordersDtos.add(ordersDto);
//        }
        page2.setRecords(ordersDtos);
        return R.success(page2);
    }

    @PutMapping
    public R<String> order(@RequestBody Orders order){
        LambdaUpdateWrapper<Orders> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Orders::getId,order.getId());
        lambdaUpdateWrapper.set(Orders::getStatus,order.getStatus());
        ordersService.update(lambdaUpdateWrapper);
        return R.success("状态更改成功");
    }
}
