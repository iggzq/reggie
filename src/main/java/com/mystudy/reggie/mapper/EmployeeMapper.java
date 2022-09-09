package com.mystudy.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mystudy.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;


//mapper文件是与数据库进行数据的交换
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
