package com.example.bank.backup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BackupController {

    private final BackupService backupService;

    @PostMapping("/admin/backup")
    public String createBackup(Model model) {
        String backupPath = backupService.createBackup();

        model.addAttribute("message", "Резервная копия создана: " + backupPath);

        return "backup/result";
    }
}