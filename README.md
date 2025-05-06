# sinopse
Este é um sistema web desenvolvido com Spring Boot para gerenciar o acervo e os 
empréstimos de uma biblioteca. A aplicação permite o cadastro de usuários (alunos e 
funcionários), controle de livros, organização por categorias e gerenciamento completo de 
empréstimos com autenticação e autorização de acesso. Ideal para uso acadêmico e 
projetos de aprendizagem de desenvolvimento web com Java.

## Sistema de Gestão de Biblioteca
Sistema de Gestão de Biblioteca com Spring Boot

## Dependencias Base 
1. Spring Web
2. Spring Data JPA
3. H2 Database
4. Spring Security
5. Thymeleaf 
6. Spring Boot DevTools (Temporaria) 

## Tabelas (JPA Entities)
1. Usuario
2. Funcionario
3. Livro
4. CategoriaLivro
5. Emprestimo


## Design Patterns (GoF)

1. Strategy 
2. Repository 

## Arquitetura 
Spring MVC 

1. Controllers 
2. Services
3. Repositories 
4. Entities 

## Autenticação e Autorização
Spring Security 

1. Login por email/senha
2. Papel ROLE_FUNCIONARIO e ROLE_USUARIO
3. Funcionalidades como empréstimos só disponíveis para usuários logados

## Interface Gráfica (GUI)
Thymeleaf 

1. Login
2. Listagem e cadastro de livros
3. Tela de empréstimos
4. Histórico de empréstimos por usuário
5. Cadastro de novos usuários (somente para funcionários) 

## Requisitos de Teste/Execução
1. Rodar com mvn spring-boot:run
2. Desenvolver os teste necessarios ( Unitarios , caixa preta/Branca .etc) 

## Estrutura de Pacotes Principal
src/main/java/br/com/biblioteca/biblioteca
├── controller
├── service
├── model
├── repository
├── security
├── strategy
└── BibliotecaApplication.java

## Spring Securit 
1. Login: user 
2. password: gerada automaticamente em cada execução , está no console. 

