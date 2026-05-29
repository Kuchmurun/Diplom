package com.example.bank.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException exception, Model model) {
        model.addAttribute("errorTitle", "Ошибка выполнения операции");
        model.addAttribute("errorMessage", exception.getMessage());

        return "errors/business-error";
    }

    @ExceptionHandler(Exception.class)
    public String handleCommonException(Exception exception, Model model) {
        model.addAttribute("errorTitle", "Внутренняя ошибка системы");
        model.addAttribute("errorMessage", "Произошла непредвиденная ошибка. Обратитесь к администратору.");

        return "errors/business-error";
    }
}