package br.com.Biblioteca.Biblioteca.controller;

import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.model.Usuario;
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

        Usuario usuarioLogado = usuarioLogadoService.getUsuarioLogado();
        List<Livro> livros;

        boolean isAdmin = usuarioLogado != null && usuarioLogado.getTipo().name().equals("ADMIN");
        boolean isFuncionario = usuarioLogado != null && usuarioLogado.getTipo().name().equals("FUNCIONARIO");

        if (busca != null && !busca.isEmpty()) {
            switch (filtro) {
                case "autor":
                    livros = (isAdmin || isFuncionario)
                            ? livroRepo.findByAutorContainingIgnoreCase(busca)
                            : livroRepo.findByAutorContainingIgnoreCaseAndAtivoTrue(busca);
                    break;
                case "categoria":
                    livros = (isAdmin || isFuncionario)
                            ? livroRepo.findByCategoriaContainingIgnoreCase(busca)
                            : livroRepo.findByCategoriaContainingIgnoreCaseAndAtivoTrue(busca);
                    break;
                default:
                    livros = (isAdmin || isFuncionario)
                            ? livroRepo.findByTituloContainingIgnoreCase(busca)
                            : livroRepo.findByTituloContainingIgnoreCaseAndAtivoTrue(busca);
            }
        } else {
            livros = (isAdmin || isFuncionario)
                    ? livroRepo.findAll()
                    : livroRepo.findByAtivoTrue();
        }

        model.addAttribute("livros", livros);
        model.addAttribute("usuarioLogado", usuarioLogado);

        return "home";
    }


}
