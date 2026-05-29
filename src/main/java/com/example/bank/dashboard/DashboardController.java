package com.example.bank.dashboard;

import com.example.bank.credit.CreditApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("clientsCount", dashboardService.countClients());
        model.addAttribute("employeesCount", dashboardService.countEmployees());
        model.addAttribute("applicationsCount", dashboardService.countApplications());
        model.addAttribute("approvedCount", dashboardService.countApplicationsByStatus(CreditApplicationStatus.APPROVED));
        model.addAttribute("rejectedCount", dashboardService.countApplicationsByStatus(CreditApplicationStatus.REJECTED));
        model.addAttribute("manualReviewCount", dashboardService.countApplicationsByStatus(CreditApplicationStatus.MANUAL_REVIEW));

        return "dashboard/index";
    }
}