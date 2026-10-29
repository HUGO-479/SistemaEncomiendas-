package org.example.encomienda.controller;

import org.example.agencia.service.AgenciaService;
import org.example.encomienda.dto.PreRegistroDTO;
import org.example.encomienda.model.Encomienda;
import org.example.encomienda.service.EncomiendaService;
import org.example.tipopaquete.repository.TipoPaqueteRepository;
import org.example.usuario.model.Usuario;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente/pre-registro")
public class PreRegistroController {

    private final AgenciaService agenciaService;
    private final TipoPaqueteRepository tipoPaqueteRepository;
    private final EncomiendaService encomiendaService;
    private final UsuarioRepository usuarioRepository;

    public PreRegistroController(AgenciaService agenciaService, TipoPaqueteRepository tipoPaqueteRepository, EncomiendaService encomiendaService, UsuarioRepository usuarioRepository) {
        this.agenciaService = agenciaService;
        this.tipoPaqueteRepository = tipoPaqueteRepository;
        this.encomiendaService = encomiendaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("agencias", agenciaService.listarTodas());
        model.addAttribute("tiposPaquete", tipoPaqueteRepository.findAll());
        model.addAttribute("preRegistro", new PreRegistroDTO());
        return "encomienda/pre-registro";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute PreRegistroDTO dto, Authentication auth, RedirectAttributes redirect) {
        Usuario clienteLogueado = usuarioRepository.findByUsername(auth.getName()).orElseThrow();

        try {
            Encomienda enc = encomiendaService.registrarPreRegistro(dto, clienteLogueado);
            redirect.addFlashAttribute("mensajeExito", "Pre-registro exitoso. Su código es: " + enc.getCodigoSeguimiento());
            return "redirect:/cliente/pre-registro";
        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/cliente/pre-registro";
        }
    }
}