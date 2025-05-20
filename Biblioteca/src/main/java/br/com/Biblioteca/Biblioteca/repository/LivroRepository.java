package br.com.Biblioteca.Biblioteca.repository;

import br.com.Biblioteca.Biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByCategoriaContainingIgnoreCase(String categoria);
    List<Livro> findByAtivoTrue();
    List<Livro> findByTituloContainingIgnoreCaseAndAtivoTrue(String titulo);
    List<Livro> findByCategoriaContainingIgnoreCaseAndAtivoTrue(String categoria);

    @Query("SELECT DISTINCT l FROM Livro l JOIN l.autores a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Livro> findByAutorNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT DISTINCT l FROM Livro l JOIN l.autores a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND l.ativo = true")
    List<Livro> findByAutorNomeContainingIgnoreCaseAndAtivoTrue(@Param("nome") String nome);


}
