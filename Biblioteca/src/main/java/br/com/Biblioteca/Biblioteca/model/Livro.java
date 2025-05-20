package br.com.Biblioteca.Biblioteca.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Livro {
    @Id
    @GeneratedValue
    private Long id;

    private String titulo;
    private String isbn;
    private String categoria;
    private int quantidade;
    private boolean ativo = true;

    @Transient
    private String autor;

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;


    public Livro() {
    }
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public List<Autor> getAutores() {return autores;}

    public void setAutores(List<Autor> autores) {this.autores = autores;}

    public String getAutor() {return autor;}

    public void setAutor(String autor) {this.autor = autor;}

    public Livro(Long id, String titulo, String isbn, String categoria, int quantidade, boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.ativo = ativo;
    }
}
