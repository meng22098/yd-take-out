package com.yundin.service;

import com.yundin.dto.*;
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

    void confirm(long id);

    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    void adminCancel(OrdersCancelDTO ordersCancelDTO);

    void delivery(Long id);

    void complete(Long id);

    void reminder(Long id);
}
