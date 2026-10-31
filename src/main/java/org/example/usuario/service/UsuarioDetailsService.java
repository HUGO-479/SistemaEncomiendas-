package org.example.usuario.service;

import org.example.usuario.model.Usuario;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<SimpleGrantedAuthority> authorities = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombre()))
                .toList();

        return User.withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .authorities(authorities)
                .disabled(!u.isActivo())
                .build();
    }
}