package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Emprestimo;
import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.model.TipoUsuario;
import br.com.Biblioteca.Biblioteca.model.Usuario;
import br.com.Biblioteca.Biblioteca.repository.EmprestimoRepository;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import br.com.Biblioteca.Biblioteca.service.UsuarioLogadoService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoRepository emprestimoRepo;

    @Autowired
    private LivroRepository livroRepo;

    @Autowired
    private UsuarioLogadoService usuarioLogadoService;


    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @GetMapping("/emprestimos/novo")
    public String novoEmprestimo(@RequestParam(required = false) Long livro,
                                 Model model) {
        Emprestimo emp = new Emprestimo();

        if (livro != null) {
            livroRepo.findById(livro).ifPresent(emp::setLivro);
        }

        model.addAttribute("emprestimo", emp);
        model.addAttribute("livros", livroRepo.findAll());

        return "form-emprestimo";
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PostMapping("/emprestimos/salvar")
    public String salvarEmprestimo(@ModelAttribute Emprestimo emprestimo) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        emprestimo.setNomeUsuario(usuario.getNome());
        emprestimo.setDataEmprestimo(LocalDate.now());

        emprestimoRepo.save(emprestimo);
        return "redirect:/emprestimos";
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @GetMapping("/emprestimos")
    public String listarEmprestimos(Model model) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        List<Emprestimo> emprestimos;

        if (usuario.getTipo() == TipoUsuario.ALUNO) {
            emprestimos = emprestimoRepo.findByNomeUsuario(usuario.getNome());
        } else {
            emprestimos = emprestimoRepo.findAll();
        }

        model.addAttribute("emprestimos", emprestimos);
        model.addAttribute("usuario", usuario);

        return "lista-emprestimos";
    }



    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @GetMapping("/emprestimos/devolver/{id}")
    public String devolverLivro(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoRepo.findById(id).orElseThrow();
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimoRepo.save(emprestimo);
        return "redirect:/emprestimos";
    }
    @GetMapping("/alugar/{id}")
    public String alugarLivro(@PathVariable Long id) {
        Livro livro = livroRepo.findById(id).orElseThrow();
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setNomeUsuario(usuario.getNome()); // CORRETO
        emprestimo.setDataEmprestimo(LocalDate.now());

        emprestimoRepo.save(emprestimo);
        return "redirect:/";
    }




}
