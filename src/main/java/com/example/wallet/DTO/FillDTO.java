package com.example.wallet.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FillDTO {
    @NotNull(message = "Айди не может быть пустым")
    @Min(value = 1, message = "Айди не может быть пустым или быть меньше 1")
    private Long id;

    @NotNull(message = "Сумма не должна быть пустой")
    @DecimalMin(value = "0.01", message = "Сумма не должна быть меньше 0.01 или пустой")
    private BigDecimal amount;

    @NotNull(message = " - Выберите валюту")
    private String currency;
}
