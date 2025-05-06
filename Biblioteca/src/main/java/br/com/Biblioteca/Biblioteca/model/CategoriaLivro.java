package br.com.Biblioteca.Biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CategoriaLivro {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;

}
