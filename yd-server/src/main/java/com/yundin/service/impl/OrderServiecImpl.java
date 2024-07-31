package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.constant.MessageConstant;
import com.yundin.context.BaseContext;
import com.yundin.dto.OrdersDTO;
import com.yundin.dto.OrdersPageQueryDTO;
import com.yundin.dto.OrdersSubmitDTO;
import com.yundin.entity.AddressBook;
import com.yundin.entity.OrderDetail;
import com.yundin.entity.Orders;
import com.yundin.entity.ShoppingCart;
import com.yundin.exception.AddressBookBusinessException;
import com.yundin.exception.ShoppingCartBusinessException;
import com.yundin.mapper.AddressBookMapper;
import com.yundin.mapper.OrderDetailMapper;
import com.yundin.mapper.OrderMapper;
import com.yundin.mapper.ShoppingCartMapper;
import com.yundin.result.PageResult;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderStatisticsVO;
import com.yundin.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiecImpl implements OrderServiec {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
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

    @Override
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        //异常情况的处理（收货地址为空、超出配送范围、购物车为空）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        //用户id
        Long userId = BaseContext.getCurrentId();
        //购物车
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);

        //查询当前用户的购物车数据
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        //构造订单数据
        Orders order = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,order);//复制
        order.setPhone(addressBook.getPhone());//手机号
        order.setAddress(addressBook.getDetail());//地址
        order.setConsignee(addressBook.getConsignee());//收货人
        order.setNumber(String.valueOf(System.currentTimeMillis()));//订单号
        order.setUserId(userId);//用户id
        order.setStatus(Orders.PENDING_PAYMENT);//状态
        order.setPayStatus(Orders.UN_PAID);//支付状态
        order.setOrderTime(LocalDateTime.now());//下单时间
        //向订单表插入1条数据
        orderMapper.insert(order);
        //订单明细数据
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();//菜品信息
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetailList.add(orderDetail);
        }
        //向明细表插入n条数据
        orderDetailMapper.insertBatch(orderDetailList);
        //清理购物车中的数据
        shoppingCartMapper.clean(userId);
        //封装返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime())
                .build();
        return orderSubmitVO;
    }
}
