package com.yundin.service.impl;
import com.yundin.dto.GoodsSalesDTO;
import com.yundin.entity.Orders;
import com.yundin.mapper.OrderMapper;
import com.yundin.mapper.UserMapper;
import com.yundin.service.ReportService;
import com.yundin.vo.OrderReportVO;
import com.yundin.vo.SalesTop10ReportVO;
import com.yundin.vo.TurnoverReportVO;
import com.yundin.vo.UserReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import org.apache.commons.lang.StringUtils;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    /**
     * 营业额数据统计
     * @param begin
     * @param end
     * @return
     */
    @Override
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end) {
            List<LocalDate> dateList = dale(begin,end);//时间处理
            List<Double> turnoverList = new ArrayList<>();//数据
            for (LocalDate date : dateList) {
                LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);//0.0
                LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);//24.0
                Map map = new HashMap();
                map.put("status", Orders.COMPLETED);
                map.put("begin",beginTime);
                map.put("end", endTime);
                Double turnover = orderMapper.sumByMap(map);//获取数据
                turnover = turnover == null ? 0.0 : turnover;//判断为不为空
                turnoverList.add(turnover);//插入数据
            }
            //数据封装
            return TurnoverReportVO.builder().dateList(StringUtils.join(dateList,",")).turnoverList(StringUtils.join(turnoverList,",")).build();
    }

    /**
     * 用户统计接口
     * @param begin
     * @param end
     * @return
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = dale(begin,end);
        List<Integer> newUserList = new ArrayList<>(); //新增用户数
        List<Integer> totalUserList = new ArrayList<>(); //总用户数
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            //新增用户数量 select count(id) from user where create_time > ? and create_time < ?
            Integer newUser =userMapper.getUserCount(beginTime, endTime);
            //总用户数量 select count(id) from user where  create_time < ?
            Integer totalUser =userMapper.getUserCount(null, endTime);
            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }
        return UserReportVO.builder().dateList(StringUtils.join(dateList,",")).newUserList(StringUtils.join(newUserList,",")).totalUserList(StringUtils.join(totalUserList,",")).build();
    }

    /**
     * 订单统计接口
     * @param begin
     * @param end
     * @return
     */
    @Override
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = dale(begin,end);
        //每天订单总数集合
        List<Integer> orderCountList = new ArrayList<>();
        //每天有效订单数集合
        List<Integer> validOrderCountList = new ArrayList<>();
        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            //查询每天的总订单数 select count(id) from orders where order_time > ? andorder_time < ?
            Integer orderCount = orderMapper.countByMap(beginTime, endTime, null);
            //查询每天的有效订单数 select count(id) from orders where order_time > ? andorder_time < ? and status = ?
            Integer validOrderCount =orderMapper.countByMap(beginTime, endTime, Orders.COMPLETED);
            orderCountList.add(orderCount);
            validOrderCountList.add(validOrderCount);
        }
        //时间区间内的总订单数
        Integer totalOrderCount = orderCountList.stream().reduce(Integer::sum).get();
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();
        //订单完成率
        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0){
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
        }
        return OrderReportVO.builder().dateList(StringUtils.join(dateList, ",")).orderCountList(StringUtils.join(orderCountList, ",")).validOrderCountList(StringUtils.join(validOrderCountList, ",")).totalOrderCount(totalOrderCount).validOrderCount(validOrderCount).orderCompletionRate(orderCompletionRate).build();
    }

    /**
     * 销量排名统计
     * @param begin
     * @param end
     * @return
     */
    @Override
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);
        List<GoodsSalesDTO> goodsSalesDTOList = orderMapper.getSalesTop10(beginTime, endTime);
        String nameList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getName).collect(Collectors.toList()),",");
        String numberList = StringUtils.join(goodsSalesDTOList.stream().map(GoodsSalesDTO::getNumber).collect(Collectors.toList()),",");
        return SalesTop10ReportVO.builder().nameList(nameList).numberList(numberList).build();
    }
    /**
     * 时间处理
     * @param begin
     * @param end
     * @return
     */
    private List<LocalDate> dale(LocalDate begin,LocalDate end)
    {
        List<LocalDate> dateList = new ArrayList<>();//时间集合
        dateList.add(begin);//获取开始时间
        while (!begin.equals(end)){
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            dateList.add(begin);//每天的时间加+1天
        }
        return dateList;
    }
}
