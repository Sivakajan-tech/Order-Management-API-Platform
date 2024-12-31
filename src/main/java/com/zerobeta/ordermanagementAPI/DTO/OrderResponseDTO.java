package com.zerobeta.ordermanagementAPI.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.Model.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private UUID id;
    private String itemName;
    private int quantity;
    private OrderStatus status;
    private LocalDateTime placed_time;

    // Factory method for creating an instance from Order
    public static OrderResponseDTO fromOrder(Order order) {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getOrderId());
        orderResponseDTO.setItemName(order.getOrderName());
        orderResponseDTO.setQuantity(order.getQuantity());
        orderResponseDTO.setStatus(order.getStatus());
        orderResponseDTO.setPlaced_time(order.getCreatedAt());

        return orderResponseDTO;
    }
}
