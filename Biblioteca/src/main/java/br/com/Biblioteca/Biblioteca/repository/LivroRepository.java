package br.com.Biblioteca.Biblioteca.repository;

import br.com.Biblioteca.Biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorContainingIgnoreCase(String autor);
    List<Livro> findByCategoriaContainingIgnoreCase(String categoria);
    List<Livro> findByAtivoTrue();
    List<Livro> findByTituloContainingIgnoreCaseAndAtivoTrue(String titulo);
    List<Livro> findByAutorContainingIgnoreCaseAndAtivoTrue(String autor);
    List<Livro> findByCategoriaContainingIgnoreCaseAndAtivoTrue(String categoria);
}
