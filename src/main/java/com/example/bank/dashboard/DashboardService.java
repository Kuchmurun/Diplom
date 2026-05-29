package com.example.bank.dashboard;

import com.example.bank.client.ClientRepository;
import com.example.bank.credit.CreditApplicationRepository;
import com.example.bank.credit.CreditApplicationStatus;
import com.example.bank.employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ClientRepository clientRepository;
    private final CreditApplicationRepository creditApplicationRepository;
    private final EmployeeRepository employeeRepository;

    public long countClients() {
        return clientRepository.count();
    }

    public long countEmployees() {
        return employeeRepository.count();
    }

    public long countApplications() {
        return creditApplicationRepository.count();
    }

    public long countApplicationsByStatus(CreditApplicationStatus status) {
        return creditApplicationRepository.countByStatus(status);
    }
}