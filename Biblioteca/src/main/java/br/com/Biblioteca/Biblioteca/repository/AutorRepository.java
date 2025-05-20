package br.com.Biblioteca.Biblioteca.repository;

import br.com.Biblioteca.Biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String nome);
}
