package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Autor;
import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.repository.AutorRepository;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepo;

    @Autowired
    private AutorRepository autorRepo;



    // Formulário para adicionar novo livro
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @GetMapping("/novo")
    public String novoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "form-livro";
    }

    // Enviar formulário (salvar livro)
    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @PostMapping("/salvar")
    public String salvarLivro(@ModelAttribute Livro livro) {
        // Busca autor existente pelo nome
        Autor autor = autorRepo.findByNome(livro.getAutor());

        // Se não existir, cria um novo
        if (autor == null) {
            autor = new Autor();
            autor.setNome(livro.getAutor());
            autorRepo.save(autor);
        }

        // Associa o autor ao livro
        livro.setAutores(List.of(autor));

        // Salva o livro
        livroRepo.save(livro);

        return "redirect:/";
    }



    // Editar livro existente
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editarLivro(@PathVariable Long id, Model model) {
        Livro livro = livroRepo.findById(id).orElseThrow();
        model.addAttribute("livro", livro);
        return "form-livro";
    }

    // Excluir livro
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable Long id) {
        livroRepo.deleteById(id);
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'FUNCIONARIO')")
    @GetMapping("/ativar-desativar/{id}")
    public String ativarOuDesativarLivro(@PathVariable Long id) {
        Livro livro = livroRepo.findById(id).orElseThrow();
        livro.setAtivo(!livro.isAtivo());
        livroRepo.save(livro);
        return "redirect:/";
    }



}
