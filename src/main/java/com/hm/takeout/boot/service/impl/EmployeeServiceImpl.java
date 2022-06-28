package com.hm.takeout.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hm.takeout.boot.entity.Employee;
import com.hm.takeout.boot.mapper.EmployeeMapper;
import com.hm.takeout.boot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;
}
