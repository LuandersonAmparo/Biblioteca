package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Emprestimo;
import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.model.TipoUsuario;
import br.com.Biblioteca.Biblioteca.model.Usuario;
import br.com.Biblioteca.Biblioteca.repository.EmprestimoRepository;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import br.com.Biblioteca.Biblioteca.service.UsuarioLogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
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

        return "lista-emprestimos";
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


    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'ALUNO')")
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



    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'ALUNO')")
    @GetMapping("/emprestimos/devolver/{id}")
    public String devolverLivro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Emprestimo emprestimo = emprestimoRepo.findById(id).orElseThrow();
        Livro livro = emprestimo.getLivro();

        // Impede aluno de devolver empréstimo de outro usuário
        if (usuario.getTipo() == TipoUsuario.ALUNO &&
                !emprestimo.getNomeUsuario().equals(usuario.getNome())) {
            redirectAttributes.addFlashAttribute("erro", "Você não pode devolver empréstimos de outro usuário.");
            return "redirect:/emprestimos/emprestimos";
        }

        // Só devolve se ainda não foi devolvido
        if (emprestimo.getDataDevolucao() == null) {
            emprestimo.setDataDevolucao(LocalDate.now());
            emprestimo.setHorarioDevolucao(LocalTime.now());
            emprestimoRepo.save(emprestimo);

            livro.setQuantidade(livro.getQuantidade() + 1);
            livroRepo.save(livro);

            redirectAttributes.addFlashAttribute("mensagem", "Livro devolvido com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Este empréstimo já foi devolvido.");
        }

        return "redirect:/emprestimos/emprestimos";
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO', 'ALUNO')")
    @GetMapping("/alugar/{id}")
    public String alugarLivro(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioLogadoService.getUsuarioLogado();
        Livro livro = livroRepo.findById(id).orElseThrow();

        // Bloqueia funcionário e admin de alugar
        if (usuario.getTipo() != TipoUsuario.ALUNO) {
            redirectAttributes.addFlashAttribute("erro", "Apenas alunos podem alugar livros.");
            return "redirect:/";
        }

        // Verifica se o livro está disponível
        if (livro.getQuantidade() > 0) {
            // Reduz a quantidade
            livro.setQuantidade(livro.getQuantidade() - 1);
            livroRepo.save(livro);

            // Cria o registro de empréstimo
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setLivro(livro);
            emprestimo.setNomeUsuario(usuario.getNome());
            emprestimo.setDataEmprestimo(LocalDate.now());

            emprestimoRepo.save(emprestimo);

            redirectAttributes.addFlashAttribute("mensagem", "Livro alugado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Este livro está indisponível para empréstimo.");
        }

        return "redirect:/";
    }






}
