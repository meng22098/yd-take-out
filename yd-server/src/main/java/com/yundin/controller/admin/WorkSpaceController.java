package com.yundin.controller.admin;

import com.yundin.result.Result;
import com.yundin.service.WorkSpaceService;
import com.yundin.vo.BusinessDataVO;
import com.yundin.vo.DishOverViewVO;
import com.yundin.vo.OrderOverViewVO;
import com.yundin.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RequestMapping("/admin/workspace")
@RestController
@Slf4j
@Api("工作台相关接口")
public class WorkSpaceController {
    @Autowired
    WorkSpaceService workSpaceService;
    @GetMapping("/businessData")
    @ApiOperation("查询今日运营数据")
    public Result<BusinessDataVO> businessData()
    {
        log.info("查询今日运营数据");
        //获得当天的开始时间
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        //获得当天的结束时间
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);
        BusinessDataVO businessDataVO = workSpaceService.getBusinessData(begin,end);
        return Result.success(businessDataVO);
    }
    @GetMapping("/overviewOrders")
    @ApiOperation("查询订单管理数据")
    public Result<OrderOverViewVO> orderOverView(){
        log.info("查询订单管理数据");
        return Result.success(workSpaceService.getOrderOverView());
    }
    /**
     * 查询菜品总览
     * @return
     */
    @GetMapping("/overviewDishes")
    @ApiOperation("查询菜品总览")
    public Result<DishOverViewVO> dishOverView(){
        log.info("查询菜品总览");
        return Result.success(workSpaceService.getDishOverView());
    }
    /**
     * 查询套餐总览
     * @return
     */
    @GetMapping("/overviewSetmeals")
    @ApiOperation("查询套餐总览")
    public Result<SetmealOverViewVO> setmealOverView(){
        log.info("查询套餐总览");
        return Result.success(workSpaceService.getSetmealOverView());
    }

}
