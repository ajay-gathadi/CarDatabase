package org.gathadi.cardatabase.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTService {
    private static final long EXPIRATION_TIME = 864_000_000;
    private static final String PREFIX = "Bearer";
    //Generate secret key. Only for demonstration purposes.
    //In Production, this shall read it from application configuration.
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //Generate signed JWT Token
    public String getToken(String username){
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .signWith(SECRET_KEY)
                .compact();
        return token;
    }


    // Get a token from request Authorization header,
    // verify the token, and get username
    public String getAuthUser(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (httpServletRequest != null){
            String user = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX,""))
                    .getBody()
                    .getSubject();

            if( user != null)
                return user;
        }
        return null;
    }
}
