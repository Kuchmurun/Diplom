package com.example.wallet.DTO;

import com.example.wallet.validation.OnCreate;
import com.example.wallet.validation.OnUpdate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
    @Min(groups = OnUpdate.class, value = 1, message = "Айди не может быть пустым или нулевым")
    private int id;
    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Имя не может быть пустым.")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 2, max = 30, message = "Имя не должно быть короче 2 и длинее 30 символов.")
    private String first_name;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Фамилия не может быть пустой.")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 2, max = 30, message = "Фамилия не должна быть короче 2 и длинее 30 символов.")
    private String last_name;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "Отчество не должно быть пустым.")
    @Size(groups = {OnCreate.class, OnUpdate.class}, min = 2, max = 30, message = "Отчество не должно быть короче 2 и длинее 30 символов.")
    private String middle_name;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Номер паспорта не должен быть пустым.")
    @Min(groups = {OnCreate.class, OnUpdate.class}, value = 1000000000, message = "Номер паспорта должен содержать 10 цифр")
    @Max(groups = {OnCreate.class, OnUpdate.class}, value = 9999999999L, message = "Номер паспорта должен содержать 10 цифр")
    private int passport_id;

    @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "Валюта не должна быть пустой.")
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "0.01", message = "Валюта не может быть меньше нуля или пустой.")
    private Double rub, usd, eur;

    private String walletStatus;

}