package com.mystudy.reggie.service.serivceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mystudy.reggie.entity.Employee;
import com.mystudy.reggie.mapper.EmployeeMapper;
import com.mystudy.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

}
