package org.gathadi.cardatabase.web;

import org.gathadi.cardatabase.domain.AccountCredentials;
import org.gathadi.cardatabase.service.JWTService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.jsonwebtoken.Jwts.header;

@RestController
public class LoginController {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginController(JWTService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials){
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        //Generate Token
        String jwts = jwtService.getToken(authentication.getName());

        //Build response with generated token
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,"Bearer" + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"Authorization")
                .build();
    }
}
