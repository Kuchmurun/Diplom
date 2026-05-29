package com.example.bank.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("applicationsCount", reportService.countApplications());
        model.addAttribute("approvedCount", reportService.countApprovedApplications());
        model.addAttribute("rejectedCount", reportService.countRejectedApplications());
        model.addAttribute("manualReviewCount", reportService.countManualReviewApplications());
        model.addAttribute("totalApprovedAmount", reportService.calculateTotalApprovedAmount());
        model.addAttribute("averageScore", reportService.calculateAverageScore());

        return "reports/index";
    }
}