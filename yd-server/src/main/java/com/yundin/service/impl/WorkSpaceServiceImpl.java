package com.yundin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yundin.constant.StatusConstant;
import com.yundin.entity.Orders;
import com.yundin.mapper.DishMapper;
import com.yundin.mapper.OrderMapper;
import com.yundin.mapper.SetmealMapper;
import com.yundin.mapper.UserMapper;
import com.yundin.service.WorkSpaceService;
import com.yundin.vo.BusinessDataVO;
import com.yundin.vo.DishOverViewVO;
import com.yundin.vo.OrderOverViewVO;
import com.yundin.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    /**
     * 查询今日运营数据
     * @param begin
     * @param end
     * @return
     */
    @Override
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);
        map.put("status", Orders.COMPLETED);
        //查询总订单数
        Integer totalOrderCount=orderMapper.countByMap(begin,end,null);

        //营业额
        Double turnover = orderMapper.sumByMap(map);
        turnover = turnover==null?0.0 :turnover;
        //有效订单数
        Integer validOrderCount = orderMapper.countByMap(begin,end,Orders.COMPLETED);
        Double unitPrice = 0.0;
        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //订单完成率
            orderCompletionRate = validOrderCount.doubleValue() /
                    totalOrderCount;
            //平均客单价
            unitPrice = turnover / validOrderCount;
        }
        //新增用户数
        Integer newUsers = userMapper.getUserCount(begin,end);
        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    /**
     * 查询订单管理数据
     * @return
     */
    @Override
    public OrderOverViewVO getOrderOverView() {
        LocalDateTime begin=LocalDateTime.now().with(LocalTime.MIN);
        Integer waitingOrders = orderMapper.countByMap(begin,null,Orders.TO_BE_CONFIRMED);
        //待派送
        Integer deliveredOrders = orderMapper.countByMap(begin,null,Orders.CONFIRMED);
        //已完成
        Integer completedOrders = orderMapper.countByMap(begin,null,Orders.COMPLETED);
        //已取消
        Integer cancelledOrders = orderMapper.countByMap(begin,null, Orders.CANCELLED);
        //全部订单
        Integer allOrders = orderMapper.countByMap(begin,null,null);
        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * 查询菜品总览
     * @return
     */
    @Override
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);
        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);
        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询套餐总览
     * @return
     */
    @Override
    public SetmealOverViewVO getSetmealOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);
        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);
        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}
