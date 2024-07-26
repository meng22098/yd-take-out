package com.yundin.controller.admin;

import com.yundin.constant.JwtClaimsConstant;
import com.yundin.dto.EmployeeDTO;
import com.yundin.dto.EmployeeLoginDTO;
import com.yundin.dto.EmployeePageQueryDTO;
import com.yundin.entity.Employee;
import com.yundin.properties.JwtProperties;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.EmployeeService;
import com.yundin.utils.JwtUtil;
import com.yundin.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 添加员工
     */
    @PostMapping
    @ApiOperation("新增雇员")
    public Result save(@RequestBody EmployeeDTO employeeDTO)
    {
        log.info("新增雇员:{}",employeeDTO);//日志
        employeeService.save(employeeDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO)
    {
        System.out.println(1);
        log.info("员工分页查询,参数为{}",employeePageQueryDTO);
        PageResult pageResult=employeeService.pageQuery(employeePageQueryDTO);//后续定义
        return Result.success(pageResult);
    }
    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }

}
