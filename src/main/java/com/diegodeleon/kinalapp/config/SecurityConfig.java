package com.diegodeleon.kinalapp.config;

import com.diegodeleon.kinalapp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider authProvider) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(auth -> auth
                        // Recursos estáticos
                        .requestMatchers("/css/**", "/styles.css", "/static/**", "/favicon.ico", "/logo.png").permitAll()
                        // Rutas públicas
                        .requestMatchers("/login", "/public/**").permitAll()
                        // Vistas web
                        .requestMatchers("/web/productos/nuevo/**", "/web/productos/editar/**", "/web/productos/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/web/clientes/nuevo/**", "/web/clientes/editar/**", "/web/clientes/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/web/ventas/nueva/**", "/web/ventas/ver/**", "/web/ventas/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/web/usuarios/nuevo/**", "/web/usuarios/editar/**", "/web/usuarios/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/detalle-ventas/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}