package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Emprestimo;
import br.com.Biblioteca.Biblioteca.model.Usuario;
import br.com.Biblioteca.Biblioteca.model.TipoUsuario;
import br.com.Biblioteca.Biblioteca.repository.EmprestimoRepository;
import br.com.Biblioteca.Biblioteca.repository.UsuarioRepository;
import br.com.Biblioteca.Biblioteca.service.UsuarioLogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioLogadoService usuarioLogadoService;

    @Autowired
    private EmprestimoRepository emprestimoRepo;

    @GetMapping("/novo")
    public String formularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("tipos", TipoUsuario.values()); // permite selecionar FUNCIONARIO ou ADMIN
        return "form-cadastro";
    }

    @PostMapping("/novo")
    public String salvarFuncionarioOuAdmin(@ModelAttribute Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepo.save(usuario);
        return "redirect:/";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        Usuario usuario = usuarioRepo.findByLogin(usuarioLogadoService.getUsuarioLogado().getLogin()).orElseThrow();
        model.addAttribute("usuario", usuario);

        // Emprestimos do usuário
        List<Emprestimo> emprestimos = emprestimoRepo.findByUsuario(usuario);
        model.addAttribute("emprestimos", emprestimos);

        if (usuario.getTipo() != TipoUsuario.ALUNO) {
            List<Usuario> alunos = usuarioRepo.findByTipo(TipoUsuario.ALUNO);
            List<Usuario> funcionarios = usuarioRepo.findByTipo(TipoUsuario.FUNCIONARIO);
            List<Emprestimo> todosEmprestimos = emprestimoRepo.findAll();

            model.addAttribute("alunos", alunos);
            model.addAttribute("funcionarios", funcionarios);
            model.addAttribute("todosEmprestimos", todosEmprestimos);
        }

        return "perfil";
    }

}
