package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // La ruta principal ahora muestra la bienvenida, NO te fuerza al login.
    @GetMapping("/")
    public String mostrarInicio() {
        return "index"; // Busca templates/index.html
    }
}