package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "雇员的数据模型")
public class EmployeeDTO implements Serializable {

    private Long id;
    @ApiModelProperty("雇员登录名")
    private String username;
    @ApiModelProperty("雇员真实姓名")
    private String name;
    @ApiModelProperty("雇员手机号")
    private String phone;
    @ApiModelProperty("雇员性别")
    private String sex;
    @ApiModelProperty("雇员身份证")
    private String idNumber;
}
