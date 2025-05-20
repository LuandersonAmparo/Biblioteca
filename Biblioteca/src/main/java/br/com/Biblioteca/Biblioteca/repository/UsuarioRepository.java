package br.com.Biblioteca.Biblioteca.repository;

import br.com.Biblioteca.Biblioteca.model.Autor;
import br.com.Biblioteca.Biblioteca.model.TipoUsuario;
import br.com.Biblioteca.Biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
    List<Usuario> findByTipo(TipoUsuario tipo);


}
