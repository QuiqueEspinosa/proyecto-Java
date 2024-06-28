package com.ucc.crudorders.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import com.ucc.crudorders.model.AuthenticationReq;
import com.ucc.crudorders.model.TokenInfo;
import com.ucc.crudorders.service.JwtUtilService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;




import java.util.List;
import java.util.stream.Collectors;





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
