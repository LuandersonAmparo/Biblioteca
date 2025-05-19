package br.com.Biblioteca.Biblioteca.config;

import br.com.Biblioteca.Biblioteca.repository.UsuarioRepository;
import br.com.Biblioteca.Biblioteca.service.AutenticacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/css/**", "/js/**",
                                "/usuario/novo", "/usuario/salvar",
                                "/alunos/novo", "/alunos/salvar",
                                "/h2-console/**"
                        ).permitAll()

                        // Acesso ao perfil: qualquer usuário autenticado
                        .requestMatchers("/perfil", "/alugar/**").hasAnyRole("ADMIN", "FUNCIONARIO", "ALUNO")

                        // Exceção para permitir acesso ao perfil
                        .requestMatchers("/usuarios/perfil").hasAnyRole("ADMIN", "FUNCIONARIO", "ALUNO")

                        // Permitir ativar/desativar para ADMIN e FUNCIONARIO
                        .requestMatchers("/livros/ativar-desativar/**").hasAnyRole("ADMIN", "FUNCIONARIO")

                        // Permitir cadastrar livro para ADMIN e FUNCIONARIO
                        .requestMatchers("/livros/novo", "/livros/salvar").hasAnyRole("ADMIN", "FUNCIONARIO")

                        // 🔒 Todas as outras rotas de /livros e /usuarios só para ADMIN
                        .requestMatchers("/livros/**", "/usuarios/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                );

        return http.build();
    }






    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepo) {
        return new AutenticacaoService(usuarioRepo);
    }



}
