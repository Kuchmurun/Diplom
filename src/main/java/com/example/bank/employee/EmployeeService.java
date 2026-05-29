package com.example.bank.employee;

import com.example.bank.audit.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditService auditService;

    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Сотрудник не найден: " + id));
    }

    public EmployeeEntity create(EmployeeEntity employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setEnabled(true);

        EmployeeEntity savedEmployee = employeeRepository.save(employee);

        auditService.log(
                "CREATE_EMPLOYEE",
                "Employee",
                savedEmployee.getId(),
                "Создан сотрудник: " + savedEmployee.getUsername()
        );

        return savedEmployee;
    }

    public void toggleEnabled(Long id) {
        EmployeeEntity employee = findById(id);
        employee.setEnabled(!Boolean.TRUE.equals(employee.getEnabled()));

        employeeRepository.save(employee);

        auditService.log(
                "CHANGE_EMPLOYEE_STATUS",
                "Employee",
                employee.getId(),
                "Изменён статус сотрудника: " + employee.getUsername()
        );
    }
}