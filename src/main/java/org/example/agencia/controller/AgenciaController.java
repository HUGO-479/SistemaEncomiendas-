package org.example.agencia.controller;

import org.example.agencia.model.Agencia;
import org.example.agencia.service.AgenciaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/agencias") // Todas las rutas aquí empezarán con /admin/agencias
public class AgenciaController {

    private final AgenciaService agenciaService;

    public AgenciaController(AgenciaService agenciaService) {
        this.agenciaService = agenciaService;
    }

    // Muestra la lista de agencias en una tabla
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("agencias", agenciaService.listarTodas());
        return "agencia/lista"; // Busca el archivo lista.html en templates/agencia/
    }

    // Muestra el formulario para crear una nueva agencia
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("agencia", new Agencia());
        return "agencia/formulario"; // Busca formulario.html
    }

    // Recibe los datos del formulario y los guarda
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Agencia agencia) {
        agenciaService.guardar(agencia);
        return "redirect:/admin/agencias"; // Redirige a la lista después de guardar
    }
}
