package com.example.bank.config;

import com.example.bank.employee.EmployeeEntity;
import com.example.bank.employee.EmployeeRepository;
import com.example.bank.employee.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (employeeRepository.findByUsername("admin").isEmpty()) {
            EmployeeEntity admin = EmployeeEntity.builder()
                    .fullName("System Administrator")
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .role(EmployeeRole.ADMIN)
                    .enabled(true)
                    .build();

            employeeRepository.save(admin);
        }
    }
}