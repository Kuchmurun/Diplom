package com.example.wallet.controller;

import com.example.wallet.DTO.CurrencyDTO;
import com.example.wallet.entity.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletValidationService;
import com.example.wallet.service.currency.CurrencyExchange;
import com.example.wallet.service.currency.CurrencyType;
import com.example.wallet.service.parsing.ParsingOperation;
import jakarta.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CurrencyController {

    CurrencyExchange exchange;
    ParsingOperation operation;
    WalletValidationService validationService;
    WalletRepository repository;

    CurrencyController(CurrencyExchange exchange, ParsingOperation operation, WalletValidationService validationService, WalletRepository repository) {
        this.exchange = exchange;
        this.operation = operation;
        this.validationService = validationService;
        this.repository = repository;
    }

    @GetMapping("/currency")
    public String showForm(Model model) {
        model.addAttribute("currencyDto", new CurrencyDTO());
        return "currency";
    }

    @PostMapping("/currency")
    public String currencyExchangeWallet(@Valid @ModelAttribute("currencyDto") CurrencyDTO currencyDTO, BindingResult bindingResult, Model model) {

        CurrencyType fromType = CurrencyType.valueOf(operation.parsFromOperation(currencyDTO));
        CurrencyType toType = CurrencyType.valueOf(operation.parsToOperation(currencyDTO));

        if (bindingResult.hasErrors()) {
            return "currency";
        }

        try {
            WalletEntity entity = repository.readWalletById(currencyDTO.getId());
            if (!validationService.checkAmount(entity, currencyDTO.getAmount(), fromType)) {
                model.addAttribute("errorMessage", "Сумма кошелька: " + fromType.get(entity) +
                        ", меньше запрашиваемой суммы: " + currencyDTO.getAmount());
                return "error-page";
            }
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("errorMessage", "Кошелек с ID: " + currencyDTO.getId() + " не найден.");
            return "error-page";
        }

        exchange.currencyTransaction(fromType, toType, currencyDTO.getId(), currencyDTO.getAmount());
        return "redirect:/index";
    }
}
