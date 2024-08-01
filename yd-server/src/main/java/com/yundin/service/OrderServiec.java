package com.yundin.service;

import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.dto.OrdersPaymentDTO;
import com.yundin.dto.OrdersSubmitDTO;
import com.yundin.result.PageResult;
import com.yundin.vo.OrderPaymentVO;
import com.yundin.vo.OrderStatisticsVO;
import com.yundin.vo.OrderSubmitVO;

public interface OrderServiec {
    PageResult pgaeQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO);
}
