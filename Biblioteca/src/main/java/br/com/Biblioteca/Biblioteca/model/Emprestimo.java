package br.com.Biblioteca.Biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;

    private String nomeUsuario;

    private LocalDate dataEmprestimo;

    private LocalDate dataDevolucao;

    private LocalTime horarioDevolucao;

    public Emprestimo() {}

    public Emprestimo(Livro livro, String nomeUsuario, LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        this.livro = livro;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public LocalTime getHorarioDevolucao() {return horarioDevolucao;}

    public void setHorarioDevolucao(LocalTime horarioDevolucao) {this.horarioDevolucao = horarioDevolucao;}

    public Emprestimo(Long id, Livro livro, String nomeUsuario, LocalDate dataEmprestimo, LocalDate dataDevolucao, LocalTime horarioDevolucao) {
        this.id = id;
        this.livro = livro;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.horarioDevolucao = horarioDevolucao;
    }
}
