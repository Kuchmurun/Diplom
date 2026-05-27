package com.example.bank.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public String listClients(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("clients", clientService.searchByFullName(search));
        model.addAttribute("search", search);
        return "clients/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("client", new ClientEntity());
        model.addAttribute("genders", Gender.values());
        return "clients/form";
    }

    @PostMapping
    public String create(@ModelAttribute ClientEntity client) {
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        return "clients/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientService.findById(id));
        model.addAttribute("genders", Gender.values());
        return "clients/form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        clientService.softDelete(id);
        return "redirect:/clients";
    }
}