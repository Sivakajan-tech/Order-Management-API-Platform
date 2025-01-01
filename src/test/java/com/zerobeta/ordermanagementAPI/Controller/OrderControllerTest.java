package com.zerobeta.ordermanagementAPI.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.OrderResponseDTO;
import com.zerobeta.ordermanagementAPI.Fixture.DTOFixture;
import com.zerobeta.ordermanagementAPI.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    void setUp() {
        orderResponseDTO = DTOFixture.createOrderResponseDTO(UUID.randomUUID());
    }

    @Test
    void testGetAllOrders() {
        List<OrderResponseDTO> mockOrders = Arrays.asList(
                new OrderResponseDTO(),
                new OrderResponseDTO());

        when(orderService.getAllOrders()).thenReturn(mockOrders);

        ResponseEntity<List<OrderResponseDTO>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrders, response.getBody());
    }

    @Test
    public void testGetOrderByIdValidId() {
        UUID id = UUID.randomUUID();
        OrderResponseDTO mockOrder = DTOFixture.createOrderResponseDTO(id);
        mockOrder.setId(id);

        when(orderService.getOrder(id)).thenReturn(mockOrder);

        ResponseEntity<OrderResponseDTO> response = orderController.getOrder(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    public void testCreateOrderValidData() {
        OrderRequestDTO orderRequest = DTOFixture.createOrderRequestDTO();
        // Set valid data for orderRequest

        when(orderService.createOrder(orderRequest)).thenReturn(orderResponseDTO);
        OrderResponseDTO mockOrder = orderService.createOrder(orderRequest);

        ResponseEntity<OrderResponseDTO> response = orderController.ordeOrder(orderRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockOrder, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCancelOrderValidId() {
        UUID id = UUID.randomUUID();

        when(orderService.cancelOrder(id)).thenReturn(orderResponseDTO);

        OrderResponseDTO mockOrder = orderService.cancelOrder(id);

        ResponseEntity<OrderResponseDTO> response = orderController.cancelOrder(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockOrder, response.getBody());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetOrderHistory() {
        List<OrderResponseDTO> mockHistory = Arrays.asList(
                new OrderResponseDTO(),
                new OrderResponseDTO());

        when(orderService.getOrderHistory(1, 5)).thenReturn(mockHistory);

        ResponseEntity<List<OrderResponseDTO>> response = orderController.getOrderHistory(1, 5);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockHistory, response.getBody());
        assertEquals(mockHistory.size(), 2);
        assertNotNull(response.getBody());
    }
}
