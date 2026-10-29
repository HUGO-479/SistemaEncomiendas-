package org.example.misenvios.controller;

import org.example.encomienda.model.Encomienda;
import org.example.misenvios.service.MisEnviosService;
import org.example.usuario.model.Usuario;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mis-envios")
public class MisEnviosController {

    private final MisEnviosService misEnviosService;
    private final UsuarioRepository usuarioRepository;

    public MisEnviosController(MisEnviosService misEnviosService, UsuarioRepository usuarioRepository) {
        this.misEnviosService = misEnviosService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarMisEnvios(Authentication auth, Model model) {
        Usuario clienteLogueado = usuarioRepository.findByUsername(auth.getName()).orElseThrow();
        List<Encomienda> envios = misEnviosService.obtenerEnviosDelCliente(clienteLogueado);

        model.addAttribute("envios", envios);
        return "misenvios/lista"; // Busca templates/misenvios/lista.html
    }
}
