package br.com.Biblioteca.Biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Funcionario {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Usuario usuario;

    private String matricula;
    private String cargo;
}
