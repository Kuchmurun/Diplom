package com.example.wallet.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    @Min(value = 1, message = "Айди не может быть пустым или нулевым")
    private int id;

    @NotNull(message = "Выберите операцию")
    private String operation;

    @NotNull(message = "Сумма не должна быть пустой")
    @DecimalMin(value = "0.01", message = "Сумма не должна быть пустой или нулевой")
    private Double amount;

}
