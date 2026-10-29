package org.example.consulta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConsultaController {

    // Esta ruta es pública (no requiere login)
    @GetMapping("/consulta")
    public String consultar(@RequestParam(required = false) String codigo, Model model) {
        if (codigo != null && !codigo.isEmpty()) {
            // Aquí iría la lógica del servicio para buscar en la BD.
            // Por ahora simulamos una respuesta:
            model.addAttribute("mensaje", "El paquete con código " + codigo + " está EN_TRANSITO a Arequipa.");
        }
        return "consulta/index"; // Busca index.html en templates/consulta/
    }
}
