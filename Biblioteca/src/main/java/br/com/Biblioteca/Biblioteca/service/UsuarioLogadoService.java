package br.com.Biblioteca.Biblioteca.service;

import br.com.Biblioteca.Biblioteca.model.Usuario;
import br.com.Biblioteca.Biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioLogadoService {
    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        return usuarioRepo.findByLogin(login).orElse(null);
    }
}

