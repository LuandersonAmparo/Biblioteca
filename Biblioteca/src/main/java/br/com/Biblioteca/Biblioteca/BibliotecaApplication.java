package br.com.Biblioteca.Biblioteca;

import br.com.Biblioteca.Biblioteca.model.Livro;
import br.com.Biblioteca.Biblioteca.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args);
	}
	@Bean
	CommandLineRunner init(LivroRepository livroRepo) {
		return args -> {
			if (livroRepo.count() == 0) {
				try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(getClass().getResourceAsStream("/old/lista de livros.txt"), StandardCharsets.UTF_8))) {

					String linha;
					String titulo = null, autor = null, isbn = null, categoria = null;

					while ((linha = reader.readLine()) != null) {
						linha = linha.trim();

						if (linha.startsWith("Título:")) {
							titulo = linha.replace("Título:", "").trim();
						} else if (linha.startsWith("Autor:")) {
							autor = linha.replace("Autor:", "").trim();
						} else if (linha.startsWith("ISBN:")) {
							isbn = linha.replace("ISBN:", "").trim();
						} else if (linha.startsWith("Categoria:")) {
							categoria = linha.replace("Categoria:", "").trim();

							Livro livro = new Livro();
							livro.setTitulo(titulo);
							livro.setAutor(autor);
							livro.setIsbn(isbn);
							livro.setCategoria(categoria);

							livroRepo.save(livro);
							System.out.println("✔ Livro salvo: " + titulo);
						}
					}

					System.out.println("✅ Livros importados para o banco com sucesso!");
				} catch (Exception e) {
					System.err.println("❌ Erro ao importar livros: " + e.getMessage());
				}
			} else {
				System.out.println("📚 Banco já populado, nenhum livro foi importado.");
			}
		};
	}

}
