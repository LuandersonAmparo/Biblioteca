package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import br.com.Biblioteca.Biblioteca.service.UsuarioLogadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private LivroRepository livroRepo;

    @Autowired
    private UsuarioLogadoService usuarioLogadoService;

    @GetMapping("/")
    public String home(@RequestParam(required = false) String busca,
                       @RequestParam(required = false) String filtro,
                       Model model) {

        List<Livro> livros;
        if (busca != null && !busca.isEmpty()) {
            switch (filtro) {
                case "autor":
                    livros = livroRepo.findByAutorContainingIgnoreCase(busca);
                    break;
                case "categoria":
                    livros = livroRepo.findByCategoriaContainingIgnoreCase(busca);
                    break;
                default:
                    livros = livroRepo.findByTituloContainingIgnoreCase(busca);
            }
        } else {
            livros = livroRepo.findAll();
        }

        model.addAttribute("livros", livros);
        model.addAttribute("usuarioLogado", usuarioLogadoService.getUsuarioLogado());

        return "home";
    }
}
