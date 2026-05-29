package com.example.bank.credit;

import com.example.bank.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credits")
public class CreditApplicationController {

    private final CreditApplicationService applicationService;
    private final ClientService clientService;
    private final CreditScoreResultRepository scoreResultRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("applications", applicationService.findAll());
        return "credits/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("clients", clientService.findAllActive());
        return "credits/form";
    }

    @PostMapping
    public String create(@RequestParam Long clientId,
                         @RequestParam BigDecimal requestedAmount,
                         @RequestParam Integer requestedMonths,
                         Model model) {
        try {
            CreditApplicationEntity application = applicationService.create(
                    clientId,
                    requestedAmount,
                    requestedMonths
            );

            return "redirect:/credits/" + application.getId();

        } catch (IllegalArgumentException exception) {
            model.addAttribute("clients", clientService.findAllActive());
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("selectedClientId", clientId);
            model.addAttribute("requestedAmount", requestedAmount);
            model.addAttribute("requestedMonths", requestedMonths);

            return "credits/form";
        }
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        CreditApplicationEntity application = applicationService.findById(id);

        model.addAttribute("credit", application);
        model.addAttribute("scoreResult", scoreResultRepository.findByApplicationId(id).orElse(null));

        return "credits/details";
    }

    @PostMapping("/{id}/calculate")
    public String calculate(@PathVariable Long id) {
        applicationService.calculateScore(id);
        return "redirect:/credits/" + id;
    }
}