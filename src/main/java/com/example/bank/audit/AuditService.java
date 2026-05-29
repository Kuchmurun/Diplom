package com.example.bank.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(String action, String entityType, Long entityId, String details) {
        String username = getCurrentUsername();

        AuditLogEntity log = AuditLogEntity.builder()
                .employeeUsername(username)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .details(details)
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    public List<AuditLogEntity> findAll() {
        return auditLogRepository.findAllByOrderByCreatedAtDesc();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "system";
        }

        return authentication.getName();
    }
}