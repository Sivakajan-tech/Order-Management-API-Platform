package com.zerobeta.ordermanagementAPI.Job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.zerobeta.ordermanagementAPI.Service.OrderService;

@Configuration
@EnableScheduling
public class OrderStatusUpdaterJob {
    @Autowired
    private OrderService orderService;

    // Scheduled to run every hour using cron expression
    @Scheduled(cron = "0 0 * * * ?") // Runs at the start of every hour
    public void updateNewOrdersToDispatched() {
        System.out.println("Updating new orders to dispatched");
        orderService.updateNewOrdersToDispatched();
    }
}
