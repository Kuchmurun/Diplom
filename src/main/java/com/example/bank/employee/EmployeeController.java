package com.example.bank.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        model.addAttribute("roles", EmployeeRole.values());
        return "employees/form";
    }

    @PostMapping
    public String create(@ModelAttribute EmployeeEntity employee) {
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/toggle")
    public String toggleEnabled(@PathVariable Long id) {
        employeeService.toggleEnabled(id);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        model.addAttribute("roles", EmployeeRole.values());
        return "employees/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @ModelAttribute EmployeeEntity employee) {
        employeeService.update(id, employee);
        return "redirect:/employees";
    }
}