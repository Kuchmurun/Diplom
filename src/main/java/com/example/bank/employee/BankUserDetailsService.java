package com.example.bank.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeEntity employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Сотрудник не найден: " + username));

        return User.builder()
                .username(employee.getUsername())
                .password(employee.getPassword())
                .roles(employee.getRole().name())
                .disabled(!Boolean.TRUE.equals(employee.getEnabled()))
                .build();
    }
}