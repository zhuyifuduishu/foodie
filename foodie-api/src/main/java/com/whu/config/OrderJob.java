package com.whu.config;

import com.whu.service.OrderService;
import com.whu.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoClose(){
        //轮询数据库中的订单的状态，当订单的时间超过一天时，修改订单的状态，关闭订单
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为: " +
                DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }
}
