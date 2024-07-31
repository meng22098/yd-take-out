package com.yundin.controller.admin;

import com.github.pagehelper.Page;
import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderPaymentVO;
import com.yundin.vo.OrderStatisticsVO;
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
    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> page(OrdersPageQueryDTO ordersPageQueryDTO)
    {
        log.info("订单搜索{}",ordersPageQueryDTO);
        PageResult pageResult=orderServiec.pgaeQuery(ordersPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statistics()
    {
        log.info("各个状态的订单数量统计");
        OrderStatisticsVO orderStatisticsVO=orderServiec.statistics();
        return Result.success(orderStatisticsVO);
    }
}
