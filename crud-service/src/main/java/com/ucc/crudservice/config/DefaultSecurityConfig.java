package com.ucc.crudservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ucc.crudservice.filters.JwtRequestFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class DefaultSecurityConfig {


  @Autowired
  private JwtRequestFilter jwtRequestFilter;
  @Bean
  SecurityFilterChain web(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())// desabilita csrf uasando el nuevo customizer
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/publico/**").permitAll()                
            .requestMatchers(HttpMethod.GET,"/swagger-ui/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/v3/**").permitAll()
            .requestMatchers(HttpMethod.GET,"/swagger-ui/index.html").permitAll()
            .requestMatchers("/api/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .cors(withDefaults())
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
    ;

    return http.build();
  }



  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration
      authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}

