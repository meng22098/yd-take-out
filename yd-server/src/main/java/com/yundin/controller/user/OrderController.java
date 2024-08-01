package com.yundin.controller.user;

import com.yundin.dto.OrdersPaymentDTO;
import com.yundin.dto.OrdersSubmitDTO;
import com.yundin.result.Result;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderPaymentVO;
import com.yundin.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api("用户接口")
public class OrderController {
    @Autowired
    OrderServiec orderServiec;
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
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO)
    {
        log.info("订单支付:{}",ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO= orderServiec.payment(ordersPaymentDTO);
        return Result.success(orderPaymentVO);
    }
}
