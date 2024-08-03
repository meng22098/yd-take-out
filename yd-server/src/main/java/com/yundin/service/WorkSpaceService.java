package com.yundin.service;

import com.yundin.vo.BusinessDataVO;
import com.yundin.vo.DishOverViewVO;
import com.yundin.vo.OrderOverViewVO;
import com.yundin.vo.SetmealOverViewVO;

import java.time.LocalDateTime;

public interface WorkSpaceService {
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    OrderOverViewVO getOrderOverView();

    DishOverViewVO getDishOverView();

    SetmealOverViewVO getSetmealOverView();
}
