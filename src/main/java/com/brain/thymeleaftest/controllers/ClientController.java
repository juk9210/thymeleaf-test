package com.brain.thymeleaftest.controllers;

import com.brain.thymeleaftest.entities.Client;
import com.brain.thymeleaftest.repositories.ClientRepository;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.beans.Transient;
import java.util.Collection;


@Controller
public class ClientController {
    private final ClientRepository clientRepository;
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/")
    public String index( Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "index";
    }


    @GetMapping("/new")
    public String showSignUpForm( Model model) {
        model.addAttribute("clientAdd", new Client());
        return "add-client";
    }

    @PostMapping("/add")
    public String addClient(@Valid Client client,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-client";
        }
        clientRepository.save(client);
        Iterable<Client> clients = clientRepository.findAll();
        model.addAttribute("clients", clients);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Client client = clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
        model.addAttribute("client", client);
        return "update-client";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable("id") long id,
                               @Valid Client client, BindingResult result, Model model) {
        if (result.hasErrors()) {
            client.setId(id);
            return "update-client";
        }
        clientRepository.save(client);
        model.addAttribute("clients", clientRepository.findAll());
        return "index";
    }

    @Transient
    @GetMapping("/delete/{id}")
    public String deletClient(@PathVariable("id") long id, Model model) {
        clientRepository.deleteById(id);
        model.addAttribute("clients", clientRepository.findAll());
        return "index";
    }
}
