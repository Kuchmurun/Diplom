package com.example.wallet.DTO;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDTO {
    @Min(value = 1, message = "Айди не может быть пустым или нулевым")
    private int id;
}
