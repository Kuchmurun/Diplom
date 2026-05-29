package com.example.bank.backup;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BackupService {

    public String createBackup() {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        String fileName = "pulsar_backup_" + timestamp + ".sql";
        String backupPath = "backups" + File.separator + fileName;

        ProcessBuilder processBuilder = new ProcessBuilder(
                "D:\\java\\pgAdmin 4\\runtime\\pg_dump.exe",
                "-U", "postgres",
                "-d", "pulsar",
                "-f", backupPath
        );

        processBuilder.environment().put("PGPASSWORD", "1234");

        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new IllegalStateException("Ошибка создания резервной копии базы данных");
            }

            return backupPath;

        } catch (IOException | InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Не удалось выполнить pg_dump", exception);
        }
    }
}