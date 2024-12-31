package com.zerobeta.ordermanagementAPI.Controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.OrderResponseDTO;
import com.zerobeta.ordermanagementAPI.Service.OrderService;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;

/*
 * OrderController is a REST controller that handles HTTP requests related to order management.
 * It provides endpoints to create, retrieve, and cancel orders.
 */
@RestController
// @CrossOrigin // This annotation is used to handle the request from a
// different origin
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        /*
         * Endpoint: GET /api/orders Function: Retrieve all orders.
         * Function: Retrieve all orders.
         */
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable UUID id) {
        /*
         * Endpoint: GET /api/orders/{id}
         * Function: Find an order by its unique ID.
         * 
         * @param id: The unique ID of the order.
         */
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/order")
    public ResponseEntity<OrderResponseDTO> ordeOrder(@RequestBody @Valid OrderRequestDTO orderRequest) {
        /*
         * Endpoint: POST /api/orders/order
         * Function: Create a new order.
         * 
         * @param orderRequest: The order details.
         */
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/cancel/{id}")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable UUID id) {
        /*
         * Endpoint: POST /api/orders/cancel/{id}
         * Function: Cancel an order by its unique ID.
         * 
         * @param id: The unique ID of the order.
         */
        return new ResponseEntity<>(orderService.cancelOrder(id), HttpStatus.OK);
    }

}
