package com.ucc.crudservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


// 2 anotaciones
@Configuration// esto le dice a sprint que la clase es una configuracion de spring
@EnableWebSecurity //esta anotacion nos habilita la seguridad web en nuestra app



public class DefaultSecurityConfig {       //configuracion de cadenad del filtro

    @Bean//es un objeto //es una anotacion que se utiliza  en metodos dentro de clases anotadas con @configuracion.Cuando spring contruye el proyecto y scanea la configuracion de la app,y detecta estos metodos

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ //aca definimos la cadena de filtros
    http
            .csrf(csrf -> csrf.disable())// desabilita csrf uasando el nuevo customizer
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers(HttpMethod.GET, "/api/**").permitAll() // Permitir GET requests sin autenticación
                    .requestMatchers(HttpMethod.POST, "/api/**").authenticated() // Requerir autenticación para POST
                    .requestMatchers(HttpMethod.PUT, "/api/**").authenticated() // Requerir autenticación para PUT
                    .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated() // Requerir autenticación para DELETE
                    .anyRequest().authenticated()
        )
        .httpBasic(withDefaults()); //habilita la autentifiacion basica con http
    return http.build(); //returna un objeto
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){ //esta es una interface
        return  new BCryptPasswordEncoder(); //es un algoritmo de hash que ayuda que nuestra contraseña no sea plana sino encriptada
    }
}
