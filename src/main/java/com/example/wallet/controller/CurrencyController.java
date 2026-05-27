package com.example.wallet.controller;

import com.example.wallet.DTO.CurrencyDTO;
import com.example.wallet.entity.WalletEntity;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.WalletValidationService;
import com.example.wallet.service.currency.CurrencyExchange;
import com.example.wallet.service.currency.CurrencyType;
import com.example.wallet.service.deleteService.SoftDelete;
import com.example.wallet.service.parsing.ParsingOperation;
import com.example.wallet.service.status.Status;
import com.example.wallet.service.status.StatusService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

// SOFT DELETE

// 1. Добавить в БД строку softDeleted, булевый, где будет true or false, в сущности тоже добавить. +++
// 2. Изменить операцию удаления, не удалять кошелек а помечать как softDeleted. +++
// 3. Создать сервис с проверкой, если объект помечен как softDeleted, возвращать страницу с оповощением, что кошель удален. +++
// 4. Создать отдельную кнопку, которая будет открывать страницу с удаленными объектами

@Controller
public class CurrencyController {

    private final StatusService statusService;
    CurrencyExchange exchange;
    ParsingOperation operation;
    WalletValidationService validationService;
    WalletRepository repository;
    SoftDelete deleteService;


    CurrencyController(CurrencyExchange exchange, ParsingOperation operation, WalletValidationService validationService, WalletRepository repository,
                       StatusService statusService, SoftDelete deleteService) {
        this.exchange = exchange;
        this.operation = operation;
        this.validationService = validationService;
        this.repository = repository;
        this.statusService = statusService;
        this.deleteService = deleteService;

    }

    @GetMapping("/currency")
    public String showForm(Model model) {
        model.addAttribute("currencyDto", new CurrencyDTO());
        return "currency";
    }

    @PostMapping("/currency")
    public String currencyExchangeWallet(@Valid @ModelAttribute("currencyDto") CurrencyDTO currencyDTO, BindingResult bindingResult, Model model) {
        if (deleteService.softChecker(currencyDTO.getId())) {
            return deleteService.getSoftPage();
        }

        Status s = Status.valueOf(repository.getWalletStatusById(currencyDTO.getId()));

        if (!s.canOperate()) {
            model.addAttribute("walletId", currencyDTO.getId());
            return statusService.getForStatus(s);
        }

        CurrencyType fromType = CurrencyType.valueOf(operation.parsFromOperation(currencyDTO));
        CurrencyType toType = CurrencyType.valueOf(operation.parsToOperation(currencyDTO));

        if (bindingResult.hasErrors()) {
            return "currency";
        }
        try {
            WalletEntity entity = repository.findById(currencyDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Кошелек с ID: " + currencyDTO.getId() + " не найден."));
            if (!validationService.checkAmount(entity, currencyDTO.getAmount(), fromType)) {
                model.addAttribute("errorMessage", "Сумма кошелька: " + fromType.get(entity) + ", меньше запрашиваемой суммы: " + currencyDTO.getAmount());
                return "error-page";
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Кошелек с ID: " + currencyDTO.getId() + " не найден.");
            return "error-page";
        }
        exchange.currencyExchange(fromType, toType, currencyDTO.getId(), currencyDTO.getAmount());
        return "redirect:/index";
    }
}
