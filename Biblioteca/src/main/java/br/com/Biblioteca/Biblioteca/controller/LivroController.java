package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/livros/novo") //EndPoint Adicionar
    public String novoLivro(Model model) {
        model.addAttribute("livro", new Livro());
        return "form-livro";
    }

    @PostMapping("/livros/salvar") //EndPoint Salvar
    public String salvarLivro(@ModelAttribute Livro livro) {
        livroRepo.save(livro);
        return "redirect:/";
    }

    @GetMapping("/livros/editar/{id}")//EndPoint Atualizar
    public String editarLivro(@PathVariable Long id, Model model) {
        Livro livro = livroRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("livro", livro);
        return "form-livro";
    }

    @GetMapping("/livros/excluir/{id}") //EndPoint Deletar Livro por ID
    public String excluirLivro(@PathVariable Long id) {
        livroRepo.deleteById(id);
        return "redirect:/";
    }

}
