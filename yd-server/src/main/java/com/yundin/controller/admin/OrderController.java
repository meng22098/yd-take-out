package com.yundin.controller.admin;

import com.yundin.result.Result;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderPaymentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/order")
@Slf4j
@Api("订单管理")
public class OrderController {
    @Autowired
    OrderServiec orderServiec;
    @GetMapping("conditionSearch")
    @ApiOperation("订单搜索")
    public Result page()
    {
        return Result.success();
    }
}
