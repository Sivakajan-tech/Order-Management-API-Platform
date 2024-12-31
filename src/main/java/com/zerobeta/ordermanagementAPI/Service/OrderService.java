package com.zerobeta.ordermanagementAPI.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.zerobeta.ordermanagementAPI.Repository.OrderRepo;
import com.zerobeta.ordermanagementAPI.Service.Utils.SecurityService;
import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.OrderResponseDTO;
import com.zerobeta.ordermanagementAPI.Model.Client;
import com.zerobeta.ordermanagementAPI.Model.Order;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private SecurityService securityService;

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepo.findAll().stream().map(order -> OrderResponseDTO.fromOrder(order))
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrder(UUID id) {
        Optional<Order> order = orderRepo.findByOrderId(id);
        if (order == null) {
            throw new IllegalArgumentException("Order with id " + id + " not found");
        }

        return OrderResponseDTO.fromOrder(order.get());
    }

    /**
     * This method retrieves a list of orders with the status NEW from the order
     * repository, iterates through the list, updates the status of each order to
     * DISPATCHED.
     */
    public void updateNewOrdersToDispatched() {
        List<Order> orderList = orderRepo.findByStatus(OrderStatus.NEW);
        for (Order order : orderList) {
            order.setStatus(OrderStatus.DISPATCHED);
            orderRepo.save(order);
        }
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        Client client = securityService.getAuthenticatedClient();

        Order newOrder = Order.fromOrderRequestDTO(orderRequest, client);

        return OrderResponseDTO.fromOrder(orderRepo.save(newOrder));
    }

    public OrderResponseDTO cancelOrder(UUID id) {
        Optional<Order> order = orderRepo.findByOrderId(id);
        /*
         * Only orders in NEW status can be cancelled.
        */

        if (order.isPresent()) { // Check if the order exists
            if (order.get().getStatus() == OrderStatus.NEW) { // Check if the order is in NEW status
                Order newOrder = order.get();
                newOrder.setStatus(OrderStatus.CANCELLED);
                return OrderResponseDTO.fromOrder(orderRepo.save(newOrder));
            } else {
                throw new IllegalStateException("Cannot cancel an order that is not in NEW status");
            }
        } else {
            throw new IllegalArgumentException("Order with id " + id + " not found");
        }
    }

    public List<OrderResponseDTO> getOrderHistory(int page, int size) {
        Client client = securityService.getAuthenticatedClient();

        // Create a PageRequest object to retrieve a page of orders
        PageRequest pageRequest = PageRequest.of(page, size);
        // Retrieve a page of orders for the client
        Page<Order> orderPage = orderRepo.findByClientId(client.getId(), pageRequest);

        // If the page is empty, throw an exception
        if (orderPage.isEmpty()) {
            throw new IllegalArgumentException("No orders found for client " + client.getId());
        }

        return orderPage.stream().map(order -> OrderResponseDTO.fromOrder(order))
                .collect(Collectors.toList());
    }
}
