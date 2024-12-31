package com.zerobeta.ordermanagementAPI.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zerobeta.ordermanagementAPI.Repository.OrderRepo;
import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.Order;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private SecurityService securityService;

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public Order getOrder(UUID id) {
        return orderRepo.findById(id).orElse(null);
    }

    public void updateNewOrdersToDispatched() {
        List<Order> orderList = orderRepo.findByStatus(OrderStatus.NEW);
        for (Order order : orderList) {
            order.setStatus(OrderStatus.DISPATCHED);
            orderRepo.save(order);
        }
    }

    public Order orderOrder(OrderRequestDTO orderRequest) {
        Client client = securityService.getAuthenticatedClient();

        Order newOrder = new Order();
        newOrder.setClient(client);
        newOrder.setOrderName(orderRequest.getName());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setQuantity(orderRequest.getQuantity());
        newOrder.setShipping_address(orderRequest.getShippingAddress());

        return orderRepo.save(newOrder);
    }

    public Order cancelOrder(UUID id) {
        Optional<Order> order = orderRepo.findByOrderId(id);

        if (order.isPresent()) {
            if (order.get().getStatus() == OrderStatus.NEW) {
                Order newOrder = order.get();
                newOrder.setStatus(OrderStatus.CANCELLED);
                return orderRepo.save(newOrder);
            } else {
                throw new IllegalStateException("Cannot cancel an order that is not in NEW status");
            }
        } else {
            throw new IllegalArgumentException("Order with id " + id + " not found");
        }
    }
}
