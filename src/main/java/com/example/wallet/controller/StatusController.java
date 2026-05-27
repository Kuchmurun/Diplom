package com.example.wallet.controller;

import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.status.StatusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StatusController {

    private final WalletRepository repository;
    private final StatusService service;

    StatusController(WalletRepository repository, StatusService service) {
        this.repository = repository;
        this.service = service;
    }


    // Деактивирован
    @GetMapping("/deactivated")
    public String showDeactivatedForm() {
        return "deactivated";
    }

    @PostMapping("/deactivated")
    public String changeDeactivated(@RequestParam("walletId") Long id, @RequestParam("operation") Boolean op) {
        if (op) {
            service.updateStatus(id);
            return "redirect:/index";
        }
        return "redirect:/index";
    }

    // Не подтвержден
    @GetMapping("/unconfirmed")
    public String showUncomForm() {
        return "unconfirmed";
    }

    @PostMapping("/unconfirmed")
    public String changeUncom(@RequestParam("walletId") Long id, @RequestParam("operation") Boolean op) {
        if (op) {
            service.updateStatus(id);
            return "redirect:/index";
        }
        return "redirect:/index";
    }
}
