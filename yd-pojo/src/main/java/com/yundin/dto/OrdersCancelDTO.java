package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("订单取消原因")
public class OrdersCancelDTO implements Serializable {
    @ApiModelProperty("订单id")
    private Long id;
    @ApiModelProperty("订单取消原因")
    private String cancelReason;
}
