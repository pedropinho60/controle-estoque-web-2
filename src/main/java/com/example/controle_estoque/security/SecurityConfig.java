package com.example.controle_estoque.security;

import com.example.controle_estoque.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/logout").permitAll()

                        .requestMatchers("/produtos/cadastrar/", "/produtos/atualizar/**").hasAuthority("cadastrar_produtos")
                        .requestMatchers("/produtos/remover/**").hasAuthority("remover_produtos")
                        .requestMatchers("/produtos/consulta/**", "/produtos/baixo-estoque").hasAuthority("consultar_produtos")

                        .requestMatchers("/lojas/cadastrar/", "/lojas/atualizar/**").hasAuthority("cadastrar_lojas")
                        .requestMatchers("/lojas/consulta/**").hasAuthority("consultar_lojas")
                        .requestMatchers("/lojas/remover/**").hasAuthority("remover_lojas")

                        .requestMatchers("/estoque/entrada", "/estoque/saida").hasAuthority("atualizar_estoque")
                        .requestMatchers("/estoque/historico").hasAuthority("consultar_estoque")

                        .requestMatchers("/usuarios/cadastrar", "/usuarios/atualizar/**").hasAuthority("cadastrar_usuario")
                        .requestMatchers("/usuarios/listar").hasAuthority("consultar_usuario")
                        .requestMatchers("/usuarios/remover/**").hasAuthority("remover_usuario")

                        .requestMatchers("/papeis/cadastrar", "/papeis/atualizar/**").hasAuthority("cadastrar_papel")
                        .requestMatchers("/papeis/listar/**").hasAuthority("consultar_papel")
                        .requestMatchers("/papeis/remover/**").hasAuthority("remover_papel")

                        .requestMatchers("/permissoes/cadastrar").hasAuthority("cadastrar_permissao")
                        .requestMatchers("/permissoes/listar/**").hasAuthority("consultar_permissao")
                        .requestMatchers("/permissoes/remover/**").hasAuthority("remover_permissao")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
