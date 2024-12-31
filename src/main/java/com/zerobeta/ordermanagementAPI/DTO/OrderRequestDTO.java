package com.zerobeta.ordermanagementAPI.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
    @NotBlank(message = "Name can not be empty")
    private String name;

    @NotBlank(message = "Address should not be empty")
    private String shipping_address;

    @NotNull(message = "Quantity can not be null")
    @Min(value = 1, message = "The value must be positive")
    private Integer quantity;
}
