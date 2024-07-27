package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("分类分页搜索")
public class CategoryPageQueryDTO implements Serializable {
    @ApiModelProperty("页码")
    private int page;
    @ApiModelProperty("每页记录数")
    private int pageSize;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("分类类型 1菜品分类  2套餐分类")
    private Integer type;
}
