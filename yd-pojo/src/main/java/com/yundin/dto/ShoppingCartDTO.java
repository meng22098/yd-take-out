package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel("购物卡")
public class ShoppingCartDTO implements Serializable {
    @ApiModelProperty("菜单id")
    private Long dishId;
    @ApiModelProperty("套餐id")
    private Long setmealId;
    @ApiModelProperty("菜单口味")
    private String dishFlavor;

}
