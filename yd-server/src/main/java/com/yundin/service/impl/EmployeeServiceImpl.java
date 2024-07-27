package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.constant.MessageConstant;
import com.yundin.constant.PasswordConstant;
import com.yundin.constant.StatusConstant;
import com.yundin.context.BaseContext;
import com.yundin.dto.EmployeeDTO;
import com.yundin.dto.EmployeeLoginDTO;
import com.yundin.dto.EmployeePageQueryDTO;
import com.yundin.entity.Employee;
import com.yundin.exception.AccountLockedException;
import com.yundin.exception.AccountNotFoundException;
import com.yundin.exception.PasswordErrorException;
import com.yundin.mapper.EmployeeMapper;
import com.yundin.result.PageResult;
import com.yundin.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //后期需要进行md5加密，然后再进行比对
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * 添加员工
     *
     * @param employeeDTO
     *
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee=new Employee();
        //属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);
        //设置状态
        employee.setStatus(StatusConstant.ENABLE);
        //设置密码
        String pwd=DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes());
        employee.setPassword(pwd);
        //通过公共字段设置
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //设置当前记录创建人id和修改人id
        employee.setCreateUser(BaseContext.getCurrentId());//目前写个假数据，后期修改
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);
    }
    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //开始分页查询
        //PageHelper分页插件第一个是第几页，第二个是页的大小
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page=employeeMapper.pageQuery(employeePageQueryDTO);
        //查询条数
        long total = page.getTotal();
        //结果数据
        List<Employee> records = page.getResult();
        return new PageResult(total, records);
    }
    /**
     * 启用禁用员工账号
     *
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);
        employeeMapper.update(employee);
    }
    /**
     *
     *根据id查询员工
     * @param id
     */
    @Override
    public Employee getById(Integer id) {
        Employee employee=employeeMapper.getById(id);
        //密码保密处理
        employee.setPassword("****");
        return employee;
    }
    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee=new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }

}
