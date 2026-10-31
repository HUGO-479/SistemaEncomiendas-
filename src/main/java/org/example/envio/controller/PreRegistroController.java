package org.example.envio.controller;

import org.example.agencia.service.AgenciaService;
import org.example.envio.dto.PreRegistroDTO;
import org.example.envio.model.Envio;
import org.example.envio.service.EnvioService;
import org.example.tipopaquete.repository.TipoPaqueteRepository;
import org.example.usuario.model.Usuario;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente/pre-registro")
public class PreRegistroController {

    private final AgenciaService agenciaService;
    private final TipoPaqueteRepository tipoPaqueteRepository;
    private final EnvioService envioService;
    private final UsuarioRepository usuarioRepository;

    public PreRegistroController(AgenciaService agenciaService, TipoPaqueteRepository tipoPaqueteRepository, EnvioService envioService, UsuarioRepository usuarioRepository) {
        this.agenciaService = agenciaService;
        this.tipoPaqueteRepository = tipoPaqueteRepository;
        this.envioService = envioService;
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
        try {
            // 1. Buscamos al usuario logueado
            Usuario clienteLogueado = usuarioRepository.findByUsername(auth.getName()).orElseThrow();

            // 2. Llamamos al servicio. Ahora devuelve un 'Envio', no una 'Encomienda'.
            Envio env = envioService.registrarPreRegistro(dto, clienteLogueado);

            // 3. Usamos getNumeroGuia() porque así se llama en la nueva BD
            redirect.addFlashAttribute("mensajeExito", "Pre-registro exitoso. Su código es: " + env.getNumeroGuia());
            return "redirect:/cliente/pre-registro";

        } catch (Exception e) {
            redirect.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/cliente/pre-registro";
        }
    }
}