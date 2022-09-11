package com.atguigu.ssm.mapper;

import com.atguigu.ssm.pojo.Employee;

import java.util.List;

/**
 * @author maomao
 * @create 2022-07-27 20:42
 */
public interface EmployeeMapper {

    List<Employee> getEmployeeList();
}
