package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Usuario;
import br.com.Biblioteca.Biblioteca.service.UsuarioLogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UsuarioGlobal {

    @Autowired
    private UsuarioLogadoService usuarioLogadoService;

    @ModelAttribute("usuarioLogado")
    public Usuario getUsuarioLogado() {
        return usuarioLogadoService.getUsuarioLogado();
    }
}
