package org.example;

import org.example.persona.model.Persona;
import org.example.persona.model.TipoDocumento;
import org.example.persona.repository.PersonaRepository;
import org.example.usuario.model.Rol;
import org.example.usuario.model.Usuario;
import org.example.usuario.repository.RolRepository;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final PersonaRepository personaRepo;
    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo; // <--- Aquí inyectamos el rolRepo
    private final PasswordEncoder passwordEncoder;

    // Constructor con todos los repositorios
    public DataLoader(PersonaRepository personaRepo,
                      UsuarioRepository usuarioRepo,
                      RolRepository rolRepo,
                      PasswordEncoder passwordEncoder) {
        this.personaRepo = personaRepo;
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Verificamos si ya existe la persona para no duplicarla
        boolean personaExiste = personaRepo.findByTipoDocumentoAndNumeroDocumento(TipoDocumento.DNI, "12345678").isPresent();

        if (!personaExiste) {
            // 2. Creamos la Persona
            Persona persona = new Persona();
            persona.setTipoDocumento(TipoDocumento.DNI);
            persona.setNumeroDocumento("12345678");
            persona.setNombres("Admin");
            persona.setApellidos("Sistema");
            persona = personaRepo.save(persona);

            // 3. Creamos el Rol y lo guardamos con rolRepo
            Rol rolAdmin = new Rol();
            rolAdmin.setNombre("ADMIN");
            rolAdmin.setDescripcion("Administrador del sistema");
            rolAdmin = rolRepo.save(rolAdmin); // <--- ¡Línea corregida!

            // 4. Creamos el Usuario y le asignamos el rol
            Usuario usuario = new Usuario();
            usuario.setUsername("admin");
            usuario.setPasswordHash(passwordEncoder.encode("admin123"));
            usuario.setPersona(persona);
            usuario.setRoles(List.of(rolAdmin));
            usuario.setActivo(true);

            usuarioRepo.save(usuario);

            System.out.println("HASH DE PRUEBA: " + passwordEncoder.encode("admin123"));
        } else {
            System.out.println("✅ La base de datos ya contiene datos, no se insertó nada.");
        }
    }
}