package com.example.bank.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping("/audit")
    public String audit(Model model) {
        model.addAttribute("logs", auditService.findAll());
        return "audit/list";
    }
}