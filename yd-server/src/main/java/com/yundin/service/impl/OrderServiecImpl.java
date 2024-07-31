package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.dto.OrdersDTO;
import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.entity.Orders;
import com.yundin.mapper.OrderMapper;
import com.yundin.result.PageResult;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderStatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiecImpl implements OrderServiec {
    @Autowired
    OrderMapper orderMapper;
    @Override
    public PageResult pgaeQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        if (ordersPageQueryDTO.getNumber()!=null||ordersPageQueryDTO.getPhone()!=null ||ordersPageQueryDTO.getStatus()!=null
                ||ordersPageQueryDTO.getBeginTime()!=null||ordersPageQueryDTO.getEndTime()!=null)
        {
            ordersPageQueryDTO.setPage(1);
        }
        PageHelper.startPage(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        Page<Orders> page=orderMapper.pageQuery(ordersPageQueryDTO);
        long total=page.getTotal();
        List<Orders> result=page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public OrderStatisticsVO statistics() {
        OrderStatisticsVO orderStatisticsVO=new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(orderMapper.ToBeConfirmed());//待接单数量
        orderStatisticsVO.setConfirmed(orderMapper.Confirmed());//待派送数量
        orderStatisticsVO.setDeliveryInProgress(orderMapper.DeliveryInProgress());//派送中数量
        return orderStatisticsVO;
    }
}
