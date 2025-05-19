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

        System.out.println(">>> Acessando perfil: " + usuario.getNome() + " | Tipo: " + usuario.getTipo());

        // Empréstimos do próprio usuário
        var emprestimosDoUsuario = emprestimoRepo.findByNomeUsuario(usuario.getNome());
        model.addAttribute("emprestimos", emprestimosDoUsuario);
        System.out.println(">>> Empréstimos encontrados: " + emprestimosDoUsuario.size());

        // ADMIN e FUNCIONARIO têm acesso a mais dados
        if (usuario.getTipo() == TipoUsuario.ADMIN || usuario.getTipo() == TipoUsuario.FUNCIONARIO) {
            var livros = livroRepo.findAll();
            var alunos = usuarioRepo.findByTipo(TipoUsuario.ALUNO);
            var funcionarios = usuarioRepo.findByTipo(TipoUsuario.FUNCIONARIO);
            var todosEmprestimos = emprestimoRepo.findAll();
            var usuarios = usuarioRepo.findAll();

            model.addAttribute("livros", livros);
            model.addAttribute("alunos", alunos);
            model.addAttribute("funcionarios", funcionarios);
            model.addAttribute("todosEmprestimos", todosEmprestimos);
            model.addAttribute("usuarios", usuarios);

            System.out.println(">>> Livros: " + livros.size());
            System.out.println(">>> Alunos: " + alunos.size());
            System.out.println(">>> Funcionários: " + funcionarios.size());
            System.out.println(">>> Todos os empréstimos: " + todosEmprestimos.size());
            System.out.println(">>> Total de usuários: " + usuarios.size());
        }

        return "perfil";
    }






}
