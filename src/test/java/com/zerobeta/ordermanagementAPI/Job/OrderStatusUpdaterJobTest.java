package com.zerobeta.ordermanagementAPI.Job;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zerobeta.ordermanagementAPI.Service.OrderService;

@ExtendWith(MockitoExtension.class)
class OrderStatusUpdaterJobTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderStatusUpdaterJob orderStatusUpdaterJob;

    @Test
    void testUpdateNewOrdersToDispatched() {
        orderStatusUpdaterJob.updateNewOrdersToDispatched();

        verify(orderService, times(1)).updateNewOrdersToDispatched();
    }
}