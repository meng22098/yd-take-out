package com.yundin.controller.user;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.yundin.config.AliPayConfig;
import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.dto.OrdersPaymentDTO;
import com.yundin.dto.OrdersSubmitDTO;
import com.yundin.result.PageResult;
import com.yundin.result.Result;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderPaymentVO;
import com.yundin.vo.OrderSubmitVO;
import com.yundin.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api("用户接口")
public class OrderController {
    @Autowired
    OrderServiec orderServiec;
    @Autowired
    AliPayConfig aliPayConfig;
    @GetMapping("/reminder/{id}")
    @ApiOperation("用户催单")
    public Result reminder(@PathVariable("id") Long id) {
        log.info("用户下单{}",id);
        orderServiec.reminder(id);
        return Result.success();
    }
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO)
    {
        log.info("用户下单{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO=orderServiec.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws IOException {
        OrderPaymentVO orderPaymentVO= orderServiec.payment(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }
    @GetMapping("/historyOrders")
    @ApiOperation("订单搜索")
    public Result<PageResult> page(OrdersPageQueryDTO ordersPageQueryDTO)
    {
        log.info("订单搜索{}",ordersPageQueryDTO);
        PageResult pageResult=orderServiec.userPgaeQuery(ordersPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> details(@PathVariable("id") Long id)
    {
        log.info("查询订单详情{}",id);
        OrderVO orderVO =orderServiec.details(id);
        return Result.success(orderVO);
    }
    @PutMapping("/cancel/{id}")
    @ApiOperation("取消订单")
    public Result cancel(@PathVariable("id") Long id)
    {
        log.info("取消订单{}",id);
        orderServiec.cancel(id);
        return Result.success();
    }
    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result repetition(@PathVariable Long id) {
        orderServiec.repetition(id);
        return Result.success();
    }
}
