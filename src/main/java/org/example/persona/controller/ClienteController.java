package org.example.persona.controller;

import org.example.persona.service.PersonaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteController {

    private final PersonaService personaService;

    public ClienteController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public String listar(Model model) {
        // model.addAttribute("clientes", clienteService.listarTodos()); // (Falta el método en el service)
        return "cliente/lista";
    }
}