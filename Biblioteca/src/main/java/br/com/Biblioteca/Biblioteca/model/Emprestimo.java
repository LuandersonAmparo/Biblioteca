package br.com.Biblioteca.Biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Emprestimo {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Livro livro;
}
