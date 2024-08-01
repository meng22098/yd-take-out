package com.yundin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yundin.constant.MessageConstant;
import com.yundin.context.BaseContext;
import com.yundin.dto.*;
import com.yundin.entity.AddressBook;
import com.yundin.entity.OrderDetail;
import com.yundin.entity.Orders;
import com.yundin.entity.ShoppingCart;
import com.yundin.exception.AddressBookBusinessException;
import com.yundin.exception.OrderBusinessException;
import com.yundin.exception.ShoppingCartBusinessException;
import com.yundin.mapper.AddressBookMapper;
import com.yundin.mapper.OrderDetailMapper;
import com.yundin.mapper.OrderMapper;
import com.yundin.mapper.ShoppingCartMapper;
import com.yundin.result.PageResult;
import com.yundin.service.OrderServiec;
import com.yundin.vo.OrderPaymentVO;
import com.yundin.vo.OrderStatisticsVO;
import com.yundin.vo.OrderSubmitVO;
import com.yundin.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderServiecImpl implements OrderServiec {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 用户订单查询
     * @param ordersPageQueryDTO
     * @return
     */
    @Override
    public PageResult userPgaeQuery(OrdersPageQueryDTO ordersPageQueryDTO) {
        if (ordersPageQueryDTO.getNumber()!=null||ordersPageQueryDTO.getPhone()!=null ||ordersPageQueryDTO.getStatus()!=null
                ||ordersPageQueryDTO.getBeginTime()!=null||ordersPageQueryDTO.getEndTime()!=null)
        {
            ordersPageQueryDTO.setPage(1);
        }
        PageHelper.startPage(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        Page<Orders> page=orderMapper.userPageQuery(ordersPageQueryDTO);
        long total=page.getTotal();
        List<Orders> result=page.getResult();
        List<OrderVO> list = new ArrayList();
        for (Orders orders:result)
        {
            List<OrderDetail> orderDetails=orderDetailMapper.getId(orders.getId());
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(orders, orderVO);
            log.info("订单菜品信息:{}",orderVO.getOrderDishes());
            orderVO.setOrderDetailList(orderDetails);
            String orderDishes ="";
            for (OrderDetail orderDetail:orderDetails)
            {
                orderDishes=orderDishes+orderDetail.getName();
            }
            orderVO.setOrderDishes(orderDishes);
            list.add(orderVO);
        }
        return new PageResult(total,list);
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @Override
    public OrderVO details(Long id) {
        Orders orders = orderMapper.getById(id);
        List<OrderDetail> orderDetails=orderDetailMapper.getId(orders.getId());
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        log.info("订单菜品信息:{}",orderVO.getOrderDishes());
        orderVO.setOrderDetailList(orderDetails);
        return orderVO;
    }

    /**
     * 取消订单
     * @param id
     */
    @Override
    public void cancel(Long id) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(id);
        // 校验订单是否存在
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消
        if (ordersDB.getStatus() > 2) {
            throw new
                    OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        // 订单处于待接单状态下取消，需要进行退款
        if (ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            //调用微信支付退款接口
//            weChatPayUtil.refund(
//                    ordersDB.getNumber(), //商户订单号
//                    ordersDB.getNumber(), //商户退款单号
//                    new BigDecimal(0.01),//退款金额，单位 元
//                    new BigDecimal(0.01));//原订单金额
            //支付状态修改为 退款
            orders.setPayStatus(Orders.REFUND);
        }
        // 更新订单状态、取消原因、取消时间
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason("用户取消");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     *再来一单
     * @param id
     */
    @Override
    public void repetition(Long id) {
        // 查询当前用户id
        Long userId = BaseContext.getCurrentId();
        // 根据订单id查询当前订单详情
        List<OrderDetail> orderDetailList = orderDetailMapper.getId(id);
        // 将订单详情对象转换为购物车对象
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x ->
        {
            ShoppingCart shoppingCart = new ShoppingCart();
            // 将原订单详情里面的菜品信息重新复制到购物车对象中
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());
        // 将购物车对象批量添加到数据库
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    /**
     * 接单
     * @param id
     */
    @Override
    public void confirm(long id) {
        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.CONFIRMED)
                .build();
        orderMapper.update(orders);
    }

    /**
     * 拒单
     * @param ordersRejectionDTO
     */
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(ordersRejectionDTO.getId());
        // 订单只有存在且状态为2（待接单）才可以拒单
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        //支付状态
        Integer payStatus = ordersDB.getPayStatus();
        if (payStatus == Orders.PAID) {
            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    ordersDB.getNumber(),
//                    ordersDB.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            log.info("申请退款：{}");
        }
        // 拒单需要退款，根据订单id更新订单状态、拒单原因、取消时间
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * 取消订单
     * @param ordersCancelDTO
     */
    @Override
    public void adminCancel(OrdersCancelDTO ordersCancelDTO) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(ordersCancelDTO.getId());
        //支付状态
        Integer payStatus = ordersDB.getPayStatus();
        if (payStatus == 1) {
            //用户已支付，需要退款
//            String refund = weChatPayUtil.refund(
//                    ordersDB.getNumber(),
//                    ordersDB.getNumber(),
//                    new BigDecimal(0.01),
//                    new BigDecimal(0.01));
            log.info("申请退款：{}");
        }
        // 管理端取消订单需要退款，根据订单id更新订单状态、取消原因、取消时间
        Orders orders = new Orders();
        orders.setId(ordersCancelDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * 派送订单
     * @param id
     */
    @Override
    public void delivery(Long id) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(id);
        // 校验订单是否存在，并且状态为3
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.CONFIRMED))
        {
            throw new
                    OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        // 更新订单状态,状态转为派送中
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);
        orderMapper.update(orders);
    }

    @Override
    public void complete(Long id) {
        // 根据id查询订单
        Orders ordersDB = orderMapper.getById(id);
        // 校验订单是否存在，并且状态为4
        if (ordersDB == null ||
                !ordersDB.getStatus().equals(Orders.DELIVERY_IN_PROGRESS)) {
            throw new
                    OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        // 更新订单状态,状态转为完成
        orders.setStatus(Orders.COMPLETED);
        orders.setDeliveryTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * 查询订单状态
     * @return
     */
    @Override
    public OrderStatisticsVO statistics() {
        OrderStatisticsVO orderStatisticsVO=new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(orderMapper.ToBeConfirmed());//待接单数量
        orderStatisticsVO.setConfirmed(orderMapper.Confirmed());//待派送数量
        orderStatisticsVO.setDeliveryInProgress(orderMapper.DeliveryInProgress());//派送中数量
        return orderStatisticsVO;
    }

    /**
     * 下单
     * @param ordersSubmitDTO
     * @return
     */
    @Override
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        //异常情况的处理（收货地址为空、超出配送范围、购物车为空）
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());//id获得地址
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

    /**
     * 支付
     * @param ordersPaymentDTO
     * @return
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        //商户平台订单号
        // 根据订单号查询当前用户的订单
        Orders ordersDB = orderMapper.getId(ordersPaymentDTO.getOrderNumber(), userId);
        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();
        orderMapper.update(orders);
        OrderPaymentVO orderPaymentVO= OrderPaymentVO.builder()
                .nonceStr("abc")
                .paySign("")
                .timeStamp("")
                .signType("")
                .packageStr("").build();
        return orderPaymentVO;
    }
}
