package com.yundin.service;

import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.dto.OrdersPaymentDTO;
import com.yundin.dto.OrdersSubmitDTO;
import com.yundin.result.PageResult;
import com.yundin.vo.OrderPaymentVO;
import com.yundin.vo.OrderStatisticsVO;
import com.yundin.vo.OrderSubmitVO;
import com.yundin.vo.OrderVO;

public interface OrderServiec {
    OrderStatisticsVO statistics();

    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);

    PageResult userPgaeQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO details(Long id);

    void cancel(Long id);

    void repetition(Long id);
}
