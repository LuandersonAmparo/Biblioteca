package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepo;

    @GetMapping //EndPoint Listar Livros
    public List<Livro> listarLivros() {
        return livroRepo.findAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/livros/novo")
    public String novoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "form-livro";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/livros/salvar")
    public String salvarLivro(@ModelAttribute Livro livro) {
        livroRepo.save(livro);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/livros/editar/{id}")
    public String editarLivro(@PathVariable Long id, Model model) {
        Livro livro = livroRepo.findById(id).orElseThrow();
        model.addAttribute("livro", livro);
        return "form-livro";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/livros/excluir/{id}")
    public String excluirLivro(@PathVariable Long id) {
        livroRepo.deleteById(id);
        return "redirect:/";
    }


}
