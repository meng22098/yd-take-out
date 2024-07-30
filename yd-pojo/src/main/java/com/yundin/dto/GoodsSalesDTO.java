package com.yundin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("销量数据模型")
public class GoodsSalesDTO implements Serializable {
    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("销量")
    private Integer number;
}
