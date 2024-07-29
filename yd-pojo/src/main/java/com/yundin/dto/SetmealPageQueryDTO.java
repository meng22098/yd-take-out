package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("套餐分页数据模型")
public class SetmealPageQueryDTO implements Serializable {
   @ApiModelProperty("分页")
    private int page;
    @ApiModelProperty("分页大小")
    private int pageSize;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("分类id")
    private Integer categoryId;
    @ApiModelProperty("状态 0表示禁用 1表示启用")
    private Integer status;

}
