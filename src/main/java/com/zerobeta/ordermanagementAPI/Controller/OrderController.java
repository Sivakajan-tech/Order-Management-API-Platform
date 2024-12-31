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

@RestController
// @CrossOrigin // This annotation is used to handle the request from a
// different origin
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable UUID id) {
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/order")
    public ResponseEntity<OrderResponseDTO> ordeOrder(@RequestBody @Valid OrderRequestDTO orderRequest) {
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/cancel/{id}")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable UUID id) {
        return new ResponseEntity<>(orderService.cancelOrder(id), HttpStatus.OK);
    }

}
