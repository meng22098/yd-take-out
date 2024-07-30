package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("订单拒绝原因")
public class OrdersRejectionDTO implements Serializable {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("订单拒绝原因")
    private String rejectionReason;

}
