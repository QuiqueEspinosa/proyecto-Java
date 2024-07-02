package com.ucc.crudservice.service;

import java.util.List;
import java.util.Set;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var usuario = getById(username);

    if (usuario == null) {
      throw new UsernameNotFoundException(username);
    }
    return User
        .withUsername(username)
        .password(usuario.password())
        .roles(usuario.roles().toArray(new String[0]))
        .build();
  }

  public record Usuario(String username, String password, Set<String> roles) {};

  public static Usuario getById(String username) {
    var password = "$2a$10$56VCAiApLO8NQYeOPiu2De/EBC5RWrTZvLl7uoeC3r7iXinRR1iiq";
    Usuario Nadia = new Usuario(
        "Nadia",
        password,
        Set.of("USER")
    );

    Usuario quique = new Usuario(
        "quique",
        password,
        Set.of("ADMIN")
    );
    var usuarios = List.of(Nadia, quique);

    return usuarios
        .stream()
        .filter(e -> e.username().equals(username))
        .findFirst()
        .orElse(null);
  }
}