package com.ucc.crudservice.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.ucc.crudservice.service.JwtUtilService;


@Component

/*este filtro JwtRequestFilter se encarga de interceptar cada solicitud HTTP entrante,
 extraer el token JWT del encabezado de autorización, validar el token JWT,
 cargar los detalles del usuario y establecer la autenticación en el contexto de seguridad de Spring Security si el token es válido
 . Esto permite asegurar las rutas y recursos de la aplicación basándose en la autenticación mediante tokens JWT. */

public class JwtRequestFilter extends OncePerRequestFilter {


    private UserDetailsService userDetailsService; //se utilizará para cargar los detalles del usuario durante la validación del token JWT.


    private JwtUtilService jwtUtilService;//proporciona métodos para la generación, validación y extracción de información de tokens JWT.


    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtilService jwtUtilService) {
        this.userDetailsService = userDetailsService;
        this.jwtUtilService = jwtUtilService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtilService.extractUsername(jwt);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtilService.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}