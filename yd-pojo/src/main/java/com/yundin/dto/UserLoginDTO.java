package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * C端用户登录
 */
@Data
@ApiModel("用户登录")
public class UserLoginDTO implements Serializable {

    private String code;

}
