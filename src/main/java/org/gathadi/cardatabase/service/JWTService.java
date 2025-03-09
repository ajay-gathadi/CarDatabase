package org.gathadi.cardatabase.service;


import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
public class JWTService {
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    private static final long EXPIRATION_TIME = 864_000_000;
    public static final String PREFIX = "Bearer";
    //Generate secret key. Only for demonstration purposes.
    //In Production, this shall read it from application configuration.
    //private static final Key SECRET_KEY = Keys.secretKeyFor(Jwts.SIG.HS256);
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    //Generate signed JWT Token
    public String getToken(String username){
        return Jwts.builder()
                .subject(username)
                .expiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME)))
                .signWith(SECRET_KEY)
                .compact();
    }


    // Get a token from request Authorization header,
    // verify the token, and get username
    public String getAuthUser(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        logger.info("*** Token *** : " +token);
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseUnsecuredClaims(token.replace(PREFIX, ""))
                .getPayload()
                .getSubject();
    }
}
