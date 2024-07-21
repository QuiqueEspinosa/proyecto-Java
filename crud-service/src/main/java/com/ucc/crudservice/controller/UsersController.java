package com.ucc.crudservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.ucc.crudservice.model.AuthenticationReq;
import com.ucc.crudservice.model.TokenInfo;
import com.ucc.crudservice.service.JwtUtilService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;




/*este controlador implementa un endpoint para autenticar usuarios mediante Spring Security y JWT.
 Toma las credenciales del usuario, las verifica,
  genera un token JWT si las credenciales son válidas y maneja las excepciones en caso de errores de autenticación.
*/
@RestController
@RequestMapping("publico")
@RequiredArgsConstructor
public class UsersController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping("authenticate")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(),
                            authenticationReq.getClave()));

            final UserDetails userDetails = usuarioDetailsService.loadUserByUsername(
                    authenticationReq.getUsuario());

            final String jwt = jwtUtilService.generateToken(userDetails);

            return ResponseEntity.ok(new TokenInfo(jwt));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build(); // Puedes ajustar el código de estado según sea necesario
        }
    }


}
