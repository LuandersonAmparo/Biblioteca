package br.com.Biblioteca.Biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Livro {
    @Id
    @GeneratedValue
    private Long id;

    private String titulo;
    private String autor;
    private String isbn;

    @ManyToOne
    private CategoriaLivro categoria;
}
