package com.yundin.service;

import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.result.PageResult;
import com.yundin.vo.OrderStatisticsVO;

public interface OrderServiec {
    PageResult pgaeQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();
}
