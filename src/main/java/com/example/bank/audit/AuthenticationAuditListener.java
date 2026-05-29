package com.example.bank.audit;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationAuditListener {

    private final AuditLogRepository auditLogRepository;

    @EventListener
    public void onLoginSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();

        AuditLogEntity log = AuditLogEntity.builder()
                .employeeUsername(authentication.getName())
                .action("LOGIN")
                .entityType("Employee")
                .entityId(null)
                .details("Сотрудник вошёл в систему")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }
}