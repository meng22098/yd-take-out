package com.yundin.dto;

import com.yundin.entity.OrderDetail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("订单数据模型")
public class OrdersDTO implements Serializable {
    @ApiModelProperty("订单id")
    private Long id;
    @ApiModelProperty("订单号")
    private String number;
    /**
     * 订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
     */
    @ApiModelProperty("订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;
    @ApiModelProperty("下单用户id")
    private Long userId;
    @ApiModelProperty("地址id")
    private Long addressBookId;
    /**
     * 下单时间
     */
    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;
    /**
     * 结账时间
     */
    @ApiModelProperty("结账时间")
    private LocalDateTime checkoutTime;
    /**
     * 支付方式 1微信，2支付宝
     */
    @ApiModelProperty("支付方式 1微信，2支付宝")
    private Integer payMethod;
    @ApiModelProperty("实收金额")
    private BigDecimal amount;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("收货人")
    private String consignee;
    @ApiModelProperty("订餐信息")
    private List<OrderDetail> orderDetails;

}
