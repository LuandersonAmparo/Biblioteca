package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Usuario;
import br.com.Biblioteca.Biblioteca.model.TipoUsuario;
import br.com.Biblioteca.Biblioteca.repository.EmprestimoRepository;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import br.com.Biblioteca.Biblioteca.repository.UsuarioRepository;
import br.com.Biblioteca.Biblioteca.service.UsuarioLogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private EmprestimoRepository emprestimoRepo;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UsuarioLogadoService usuarioLogadoService;
    @Autowired
    private EmprestimoRepository emprestimoRepository;
    @Autowired
    private LivroRepository livroRepo;

    @GetMapping("/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("tipos", TipoUsuario.values());
        return "form-usuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha())); // Criptografar a senha
        usuarioRepo.save(usuario);
        return "redirect:/login";
    }
    @GetMapping("/perfil")
    public String perfil(Model model) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        model.addAttribute("usuario", usuario);
        model.addAttribute("emprestimos", emprestimoRepo.findByNomeUsuario(usuario.getNome()));

        if (usuario.getTipo() == TipoUsuario.ADMIN) {
            model.addAttribute("livros", livroRepo.findAll());
            model.addAttribute("usuarios", usuarioRepo.findAll());
        }

        return "perfil";
    }




}
