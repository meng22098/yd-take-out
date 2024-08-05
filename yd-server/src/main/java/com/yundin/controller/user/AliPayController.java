package com.yundin.controller.user;

import com.alipay.easysdk.factory.Factory;
import com.yundin.config.AliPayConfig;
import com.yundin.entity.Orders;
import com.yundin.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("alipay")
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AliPayController {

    @Resource
    AliPayConfig aliPayConfig;
    @Resource
    private OrderMapper orderMapper;
    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public void pay(HttpServletResponse httpResponse) throws Exception {
        System.out.println("支付");
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.GATEWAY_URL, aliPayConfig.getAppId(), aliPayConfig.getAppPrivateKey(), aliPayConfig.FORMAT,  aliPayConfig.CHARSET, aliPayConfig.getAlipayPublicKey(),aliPayConfig.SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        //商品描述，可空
        request.setBizContent("{\"out_trade_no\":\"" +"789"+ "\"," + "\"total_amount\":\"" + 120 + "\"," + "\"subject\":\"" + 1 + "\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            // 调用SDK生成表单
            form = alipayClient.pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" +aliPayConfig.CHARSET);
        // 直接将完整的表单html输出到页面
        httpResponse.getWriter().write(form);
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        System.out.println("支付成功");
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));
                // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
                Orders orders = Orders.builder()
                        .id(Long.valueOf(tradeNo))
                        .status(Orders.TO_BE_CONFIRMED)
                        .payStatus(Orders.PAID)
                        .checkoutTime(LocalDateTime.now())
                        .build();
                orderMapper.update(orders);
            }
        }
        return "success";
    }
}
