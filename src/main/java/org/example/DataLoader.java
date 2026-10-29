package org.example;

import org.example.agencia.model.Agencia;
import org.example.agencia.repository.AgenciaRepository;
import org.example.cliente.model.Cliente;
import org.example.cliente.model.TipoDocumento;
import org.example.cliente.repository.ClienteRepository;
//import org.example.tarifa.model.Tarifa;
//import org.example.tarifa.repository.TarifaRepository;
import org.example.tipopaquete.model.TipoPaquete;
import org.example.tipopaquete.repository.TipoPaqueteRepository;
import org.example.usuario.model.Rol;
import org.example.usuario.model.Usuario;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final AgenciaRepository agenciaRepo;
    private final TipoPaqueteRepository tipoRepo;
    private final UsuarioRepository usuarioRepo;
    private final ClienteRepository clienteRepo;
  /*  private final TarifaRepository tarifaRepo;*/
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AgenciaRepository agenciaRepo, TipoPaqueteRepository tipoRepo,
                      UsuarioRepository usuarioRepo, ClienteRepository clienteRepo /*TarifaRepository tarifaRepo*/, PasswordEncoder passwordEncoder) {
        this.agenciaRepo = agenciaRepo;
        this.tipoRepo = tipoRepo;
        this.usuarioRepo = usuarioRepo;
        this.clienteRepo = clienteRepo;
    /*    this.tarifaRepo = tarifaRepo;*/
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. CREAR AGENCIAS (Si no existen)
        if (agenciaRepo.count() == 0) {
            Agencia lima = new Agencia();
            lima.setNombre("Agencia Lima Centro");
            lima.setDepartamento("Lima");
            lima.setCiudad("Lima");
            lima.setActiva(true);
            agenciaRepo.save(lima);

            Agencia arequipa = new Agencia();
            arequipa.setNombre("Agencia Arequipa");
            arequipa.setDepartamento("Arequipa");
            arequipa.setCiudad("Arequipa");
            arequipa.setActiva(true);
            agenciaRepo.save(arequipa);

            System.out.println("✅ Agencias creadas");
        }

        // 2. CREAR TIPOS DE PAQUETE
        if (tipoRepo.count() == 0) {
            TipoPaquete sobre = new TipoPaquete();
            sobre.setNombre("Sobre / Documentos");
            sobre.setDescripcion("Documentos y sobres ligeros");
            sobre.setActivo(true);
            tipoRepo.save(sobre);

            TipoPaquete cajaSmall = new TipoPaquete();
            cajaSmall.setNombre("Caja Small");
            cajaSmall.setDescripcion("Caja pequeña");
            cajaSmall.setActivo(true);
            tipoRepo.save(cajaSmall);

            TipoPaquete cajaMediana = new TipoPaquete();
            cajaMediana.setNombre("Caja Mediana");
            cajaMediana.setDescripcion("Caja mediana");
            cajaMediana.setActivo(true);
            tipoRepo.save(cajaMediana);

            TipoPaquete cajaGrande = new TipoPaquete();
            cajaGrande.setNombre("Caja Grande");
            cajaGrande.setDescripcion("Caja grande o industrial");
            cajaGrande.setActivo(true);
            tipoRepo.save(cajaGrande);

            System.out.println("✅ Tipos de paquete creados");
        }

        // 3. CREAR CLIENTES DE PRUEBA (Para que puedas simular un envío)
        if (clienteRepo.count() == 0) {
            Cliente cliente1 = new Cliente();
            cliente1.setTipoDocumento(TipoDocumento.DNI);
            cliente1.setNumeroDocumento("12345678");
            cliente1.setNombres("Juan");
            cliente1.setApellidos("Perez");
            cliente1.setCelular("987654321");
            clienteRepo.save(cliente1);

            Cliente cliente2 = new Cliente();
            cliente2.setTipoDocumento(TipoDocumento.DNI);
            cliente2.setNumeroDocumento("87654321");
            cliente2.setNombres("Maria");
            cliente2.setApellidos("Gomez");
            cliente2.setCelular("912345678");
            clienteRepo.save(cliente2);

            System.out.println("✅ Clientes de prueba creados");
        }
/*
        // 4. CREAR TARIFAS BASE (Lógica del negocio: Precio base + precio por kilo)
        if (tarifaRepo.count() == 0) {
            // Buscamos los tipos de paquete que acabamos de crear
            TipoPaquete sobre = tipoRepo.findAll().get(0);
            TipoPaquete cajaSmall = tipoRepo.findAll().get(1);
            TipoPaquete cajaMediana = tipoRepo.findAll().get(2);
            TipoPaquete cajaGrande = tipoRepo.findAll().get(3);

            // Tarifa para Sobre: Base 8, por kilo 0 (no se cobra peso)
            Tarifa t1 = new Tarifa();
            t1.setTipoPaquete(sobre);
            t1.setPrecioBase(new BigDecimal("8.00"));
            t1.setPrecioPorKg(new BigDecimal("0.00"));
            t1.setActiva(true);
            tarifaRepo.save(t1);

            // Tarifa para Caja Small: Base 10 + 1.50 por kilo
             Tarifa t2 = new Tarifa();
            t2.setTipoPaquete(cajaSmall);
            t2.setPrecioBase(new BigDecimal("10.00"));
            t2.setPrecioPorKg(new BigDecimal("1.50"));
            t2.setActiva(true);
            tarifaRepo.save(t2);

            // Tarifa para Caja Mediana: Base 15 + 2.00 por kilo
            Tarifa t3 = new Tarifa();
            t3.setTipoPaquete(cajaMediana);
            t3.setPrecioBase(new BigDecimal("15.00"));
            t3.setPrecioPorKg(new BigDecimal("2.00"));
            t3.setActiva(true);
            tarifaRepo.save(t3);

            // Tarifa para Caja Grande: Base 22 + 2.50 por kilo
            Tarifa t4 = new Tarifa();
            t4.setTipoPaquete(cajaGrande);
            t4.setPrecioBase(new BigDecimal("22.00"));
            t4.setPrecioPorKg(new BigDecimal("2.50"));
            t4.setActiva(true);
            tarifaRepo.save(t4);

            System.out.println("✅ Tarifas base creadas");
        } */

        // 5. CREAR USUARIOS DE PRUEBA (Admin, Empleado, Cliente)
        if (usuarioRepo.count() == 0) {
            Agencia agenciaLima = agenciaRepo.findAll().get(0);
            Cliente clientePrueba = clienteRepo.findAll().get(0);

            // Admin
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            admin.setActivo(true);
            usuarioRepo.save(admin);

            // Empleado (Asignado a la agencia de Lima)
            Usuario empleado = new Usuario();
            empleado.setUsername("empleado");
            empleado.setPasswordHash(passwordEncoder.encode("emp123"));
            empleado.setRol(Rol.EMPLEADO);
            empleado.setAgencia(agenciaLima);
            empleado.setActivo(true);
            usuarioRepo.save(empleado);

            // Cliente (Asociado al cliente1)
            Usuario clienteUser = new Usuario();
            clienteUser.setUsername("cliente");
            clienteUser.setPasswordHash(passwordEncoder.encode("cli123"));
            clienteUser.setRol(Rol.CLIENTE);
            clienteUser.setCliente(clientePrueba);
            clienteUser.setActivo(true);
            usuarioRepo.save(clienteUser);

            System.out.println("✅ Usuarios de prueba creados");
            System.out.println("👉 Admin: admin / admin123");
            System.out.println("👉 Empleado: empleado / emp123");
            System.out.println("👉 Cliente: cliente / cli123");
        }
    }
}