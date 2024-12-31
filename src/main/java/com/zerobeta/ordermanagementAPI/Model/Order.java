package com.zerobeta.ordermanagementAPI.Model;

import java.time.LocalDateTime;
import java.util.UUID;
import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`orders`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", columnDefinition = "BINARY(16)")
    private UUID orderId;

    @Column(name = "order_name", nullable = false)
    private String orderName;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "shipping_address", nullable = false)
    private String shipping_address;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.NEW;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Factory method for creating an instance from OrderRequestDTO
    public static Order fromOrderRequestDTO(OrderRequestDTO orderRequest, Client client) {
        Order newOrder = new Order();
        newOrder.setClient(client);
        newOrder.setOrderName(orderRequest.getName());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setQuantity(orderRequest.getQuantity());
        newOrder.setShipping_address(orderRequest.getShipping_address());
        return newOrder;
    }

}
