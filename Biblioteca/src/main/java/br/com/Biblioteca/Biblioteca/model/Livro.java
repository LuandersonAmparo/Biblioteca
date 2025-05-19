package br.com.Biblioteca.Biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Livro {
    @Id
    @GeneratedValue
    private Long id;

    private String titulo;
    private String autor;
    private String isbn;
    private String categoria;
    private int quantidade;
    private boolean ativo = true;

    public Livro() {
    }
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {this.categoria = categoria;}

    public int getQuantidade() {return quantidade;}

    public void setQuantidade(int quantidade) {this.quantidade = quantidade;}

    public boolean isAtivo() {return ativo;}

    public void setAtivo(boolean ativo) {this.ativo = ativo;}


    public Livro(Long id, String titulo, String autor, String isbn, String categoria, int quantidade, boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.ativo = ativo;
    }
}
