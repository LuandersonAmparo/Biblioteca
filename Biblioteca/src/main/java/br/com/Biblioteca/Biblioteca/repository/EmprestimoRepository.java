package br.com.Biblioteca.Biblioteca.repository;

import br.com.Biblioteca.Biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByNomeUsuario(String nomeUsuario);

}
