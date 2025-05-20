package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.TipoUsuario;
import br.com.Biblioteca.Biblioteca.model.Usuario;
import br.com.Biblioteca.Biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/novo")
    public String novoAluno(Model model) {
        Usuario aluno = new Usuario();
        aluno.setTipo(TipoUsuario.ALUNO); // já define o tipo automaticamente
        model.addAttribute("usuario", aluno);
        return "form-usuario";
    }

    @PostMapping("/salvar")
    public String salvarAluno(@ModelAttribute Usuario usuario) {
        usuario.setTipo(TipoUsuario.ALUNO);
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        usuarioRepo.save(usuario);
        return "redirect:/login";
    }
}
