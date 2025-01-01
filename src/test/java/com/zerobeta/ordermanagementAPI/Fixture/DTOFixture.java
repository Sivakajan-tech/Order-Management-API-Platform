package com.zerobeta.ordermanagementAPI.Fixture;

import java.time.LocalDateTime;
import java.util.UUID;

import com.zerobeta.ordermanagementAPI.Common.Enums.OrderStatus;
import com.zerobeta.ordermanagementAPI.DTO.LoginRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.OrderRequestDTO;
import com.zerobeta.ordermanagementAPI.DTO.OrderResponseDTO;
import com.zerobeta.ordermanagementAPI.DTO.RegisterRequestDTO;

public class DTOFixture {

    public static RegisterRequestDTO createRegisterRequestDTO() {
        return new RegisterRequestDTO("FirstUserName", "LastUserName", "email@email.com", "Password");
    }

    public static LoginRequestDTO createLoginRequestDTO() {
        return new LoginRequestDTO("email@email.com", "Password");
    }

    public static OrderResponseDTO createOrderResponseDTO(UUID id) {
        return new OrderResponseDTO(id, "OrderName", 1, OrderStatus.NEW, LocalDateTime.now());
    }

    public static OrderRequestDTO createOrderRequestDTO() {
        return new OrderRequestDTO("OrderName", "ShippingAddress", 1);
    }
}
