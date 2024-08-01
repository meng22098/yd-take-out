package com.yundin.mapper;

import com.github.pagehelper.Page;
import com.yundin.dto.OrdersDTO;
import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.entity.Orders;
import com.yundin.vo.OrderStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);
    @Select("select count(*) from orders where status=3")
    Integer Confirmed();
    @Select("select count(*) from orders where status=2")
    Integer ToBeConfirmed();
    @Select("select count(*) from orders where status=4")
    Integer DeliveryInProgress();

    void insert(Orders order);
    @Select("select * from orders where number = #{orderNumber} and user_id=#{userId}")
    Orders getId(String orderNumber, Long userId);

    void update(Orders orders);
}
