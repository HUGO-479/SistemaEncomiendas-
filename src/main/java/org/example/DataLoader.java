package org.example;

import org.example.persona.model.Persona;
import org.example.persona.repository.PersonaRepository;
import org.example.usuario.model.Rol;
import org.example.usuario.model.Usuario;
import org.example.usuario.repository.RolRepository;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.example.persona.model.TipoDocumento;

@Component
public class DataLoader implements CommandLineRunner {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private final RolRepository rolRepo;
    private final PersonaRepository personaRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder encoder;

    public DataLoader(RolRepository rolRepo,
                      PersonaRepository personaRepo,
                      UsuarioRepository usuarioRepo,
                      PasswordEncoder encoder) {
        this.rolRepo = rolRepo;
        this.personaRepo = personaRepo;
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Rol rolAdmin = crearRolSiNoExiste("ADMIN", "Administrador del sistema");
        crearRolSiNoExiste("EMPLEADO", "Personal de agencia");
        crearRolSiNoExiste("CLIENTE", "Cliente o remitente");

        crearAdminSiNoExiste(rolAdmin);
    }

    private Rol crearRolSiNoExiste(String nombre, String descripcion) {
        return rolRepo.findByNombre(nombre).orElseGet(() -> {
            Rol rol = new Rol();
            rol.setNombre(nombre);
            rol.setDescripcion(descripcion);
            return rolRepo.save(rol);
        });
    }

    private void crearAdminSiNoExiste(Rol rolAdmin) {
        if (usuarioRepo.findByUsername(ADMIN_USERNAME).isPresent()) {
            return;
        }

        Persona persona = new Persona();
        persona.setTipoDocumento(TipoDocumento.DNI);   // ajusta al enum real de tu proyecto
        persona.setNumeroDocumento("00000001");
        persona.setNombres("Administrador");
        persona.setActivo(true);
        persona = personaRepo.save(persona);           // guardar antes de asociar

        Usuario usuario = new Usuario();
        usuario.setPersona(persona);
        usuario.setUsername(ADMIN_USERNAME);
        usuario.setPasswordHash(encoder.encode(ADMIN_PASSWORD));
        usuario.setActivo(true);
        usuario.getRoles().add(rolAdmin);
        usuarioRepo.save(usuario);
    }
}