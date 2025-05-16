package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Emprestimo;
import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.repository.EmprestimoRepository;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/novo")
    public String novoEmprestimo(Model model) {
        model.addAttribute("emprestimo", new Emprestimo());
        model.addAttribute("livros", livroRepo.findAll());
        return "form-emprestimo";
    }

    @PostMapping("/salvar")
    public String salvarEmprestimo(@ModelAttribute Emprestimo emprestimo) {
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimoRepo.save(emprestimo);
        return "redirect:/emprestimos";
    }

    @GetMapping
    public String listarEmprestimos(Model model) {
        List<Emprestimo> lista = emprestimoRepo.findAll();
        model.addAttribute("emprestimos", lista);
        return "lista-emprestimos";
    }

    @GetMapping("/devolver/{id}")
    public String devolverLivro(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimoRepo.save(emprestimo);
        return "redirect:/emprestimos";
    }
}
