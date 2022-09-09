package com.mystudy.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mystudy.reggie.entity.Employee;


//service文件是调用mapper获取数据 并被 controller调用
public interface EmployeeService extends IService<Employee> {

}
