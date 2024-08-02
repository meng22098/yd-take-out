package com.yundin.service;

import com.yundin.vo.OrderReportVO;
import com.yundin.vo.SalesTop10ReportVO;
import com.yundin.vo.TurnoverReportVO;
import com.yundin.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);

    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end);
}
