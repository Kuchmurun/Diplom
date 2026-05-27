package com.example.wallet.controller;

import com.example.wallet.DTO.DeleteDTO;
import com.example.wallet.DTO.FillDTO;
import com.example.wallet.DTO.WalletDTO;
import com.example.wallet.repository.WalletRepository;
import com.example.wallet.service.FillWallet;
import com.example.wallet.service.WalletService;
import com.example.wallet.service.WalletValidationService;
import com.example.wallet.service.deleteService.SoftDelete;
import com.example.wallet.service.status.Status;
import com.example.wallet.service.status.StatusService;
import com.example.wallet.validation.OnCreate;
import com.example.wallet.validation.OnUpdate;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WalletController {

    private final WalletRepository repository;

    private final WalletService service;

    private final WalletValidationService validationService;

    private final FillWallet fillWallet;

    private final StatusService statusService;

    SoftDelete deleteService;

    WalletController(WalletRepository repository, WalletService service, WalletValidationService validationService, FillWallet fillWallet,
                     StatusService statusService, SoftDelete deleteService) {
        this.repository = repository;
        this.service = service;
        this.validationService = validationService;
        this.fillWallet = fillWallet;
        this.statusService = statusService;
        this.deleteService = deleteService;
    }

    // Главная страница
    @GetMapping("/index")
    public String showWallets(Model model) {
        model.addAttribute("wallets", repository.findNotDeleted());
        return "index";
    }

    @GetMapping("/indexDeleted")
    public String showDeleted(Model model) {
        model.addAttribute("wallets", repository.findDeleted());
        return "indexDeleted";
    }

    // Пополнение кошелька
    @GetMapping("/fill")
    public String showFillForm(Model model) {
        model.addAttribute("fillDto", new FillDTO());
        return "fill";
    }

    @PostMapping("/fill")
    public String fillBalanceWallet(@Valid @ModelAttribute("fillDto") FillDTO fillDto, BindingResult bindingResult, Model model) {

        if (deleteService.softChecker(fillDto.getId())) {
            return deleteService.getSoftPage();
        }

        Status s = Status.valueOf(repository.getWalletStatusById(fillDto.getId()));

        if (!s.canOperate()) {
            model.addAttribute("walletId", fillDto.getId());
            return statusService.getForStatus(s);
        }

        if (bindingResult.hasErrors()) {
            return "fill";
        }

        if (!validationService.checkId(fillDto.getId())) {
            model.addAttribute("errorMessage", "Кошелек с ID: " + fillDto.getId() + " не найден.");
            return "error-page";
        }

//        repository.fillBalance(fillDto.getId(), fillDto.getAmount(), fillDto.getCurrency());
        fillWallet.fill(fillDto.getId(), fillDto.getAmount(), fillDto.getCurrency());


        return "redirect:/index";
    }

    // Обновление кошелька
    @GetMapping("/update")
    public String showUpdateForm(Model model) {
        model.addAttribute("walletDTO", new WalletDTO());
        return "update";
    }

    @PostMapping("/update")
    public String updateWallet(@Validated(OnUpdate.class) @ModelAttribute("walletDTO") WalletDTO walletDTO, BindingResult bindingResult,
                               Model model) {
        if (deleteService.softChecker(walletDTO.getId())) {
            return deleteService.getSoftPage();
        }

        Status s = Status.valueOf(repository.getWalletStatusById(walletDTO.getId()));

        if (!s.canOperate()) {
            model.addAttribute("walletId", walletDTO.getId());
            return statusService.getForStatus(s);
        }

        if (bindingResult.hasErrors()) {
            return "update";
        }

        if (!validationService.checkId(walletDTO.getId())) {
            model.addAttribute("errorMessage", "Кошелек с ID: " + walletDTO.getId() + " не найден.");
            return "error-page";
        }
        service.updateWallet(walletDTO);
        return "redirect:/index";
    }

    // Добавление кошелька
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("walletDTO", new WalletDTO());
        return "create";
    }


    @PostMapping("/create")
    public String createWallet(@Validated(OnCreate.class) @ModelAttribute WalletDTO walletDTO, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "create";
        }
        service.createWallet(walletDTO);
        return "redirect:/index";
    }

    // Удаление кошелька
    @GetMapping("/delete")
    public String showDeleteForm(Model model) {
        model.addAttribute("deleteDto", new DeleteDTO());
        return "delete";
    }

    @PostMapping("/delete")
    public String deleteForm(@Valid @ModelAttribute("deleteDto") DeleteDTO deleteDto, BindingResult bindingResult, Model model) {
        Status s = Status.valueOf(repository.getWalletStatusById(deleteDto.getId()));

        if (!s.canOperate()) {
            model.addAttribute("walletId", deleteDto.getId());
            return statusService.getForStatus(s);
        }
        if (bindingResult.hasErrors()) {
            return "delete";
        }

        if (!validationService.checkId(deleteDto.getId())) {
            model.addAttribute("errorMessage", "Кошелек с ID: " + deleteDto.getId() + " не найден.");
            return "error-page";
        }

        deleteService.deleteById(deleteDto.getId());
        return "redirect:/index";
    }
}
