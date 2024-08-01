package com.yundin.controller.admin;

import com.github.pagehelper.Page;
import com.yundin.dto.OrdersCancelDTO;
import com.yundin.dto.OrdersConfirmDTO;
import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.dto.OrdersRejectionDTO;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderPaymentVO;
import com.yundin.vo.OrderStatisticsVO;
import com.yundin.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        PageResult pageResult=orderServiec.userPgaeQuery(ordersPageQueryDTO);
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
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable("id") Long id)
    {
        log.info("查询订单详情{}",id);
        OrderVO orderVO =orderServiec.details(id);
        return Result.success(orderVO);
    }
    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO)
    {
        log.info("接单{}",ordersConfirmDTO);
        orderServiec.confirm(ordersConfirmDTO.getId());
        return Result.success();
    }
    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO)
    {
        log.info("拒单{}",ordersRejectionDTO);
        orderServiec.rejection(ordersRejectionDTO);
        return Result.success();
    }
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO){
        log.info("拒单{}",ordersCancelDTO);
        orderServiec.adminCancel(ordersCancelDTO);
        return Result.success();
    }
    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result delivery(@PathVariable("id") Long id) {
        log.info("派送订单{}",id);
        orderServiec.delivery(id);
        return Result.success();
    }
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result complete(@PathVariable("id") Long id) {
        orderServiec.complete(id);
        return Result.success();
    }

}
