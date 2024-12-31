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
import com.zerobeta.ordermanagementAPI.Model.Order;
import com.zerobeta.ordermanagementAPI.Service.OrderService;

@RestController
// @CrossOrigin // This annotation is used to handle the request from a
// different origin
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID id) {
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }

    @PostMapping("/order")
    public Order ordeOrder(@RequestBody OrderRequestDTO orderRequest) {
        return orderService.orderOrder(orderRequest);
    }

    @PostMapping("/cancel/{id}")
    public Order cancelOrder(@PathVariable UUID id) {
        return orderService.cancelOrder(id);
    }

}