package com.yundin.service;

import com.yundin.dto.EmployeeDTO;
import com.yundin.dto.EmployeeLoginDTO;
import com.yundin.dto.EmployeePageQueryDTO;
import com.yundin.entity.Employee;
import com.yundin.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void save(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void startOrStop(Integer status, Long id);

    Employee getById(Integer id);

    void update(EmployeeDTO employeeDTO);
}
