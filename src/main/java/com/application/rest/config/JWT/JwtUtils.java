package com.application.rest.config.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String secretKey;  //<--- nos va a permitir firmar el metodo, es para validar un token
    @Value("${jwt.time.expiration}")
    private String timeExpiration; //<--- Tiempo de validez de token en milisegundos

    public String generateAccesToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis())) //Cuando genero el token
                .expiration(new Date(System.currentTimeMillis()+Long.parseLong(timeExpiration))) //Cuando vence
                .signWith(getSignatureKey()) //Como se encriptada
                .compact();
    }
    public boolean isTokenValid(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSignatureKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true; //Token valido ya que no hay error al realizar la operacion
        }catch (Exception e)
        {
            log.error("Token invalido, error: " + e.getMessage());
            return false;
        }

    }
    public String getUsernameFromToken(String token)
    {
        return getClaim(token,Claims::getSubject);
    }
    public Claims extractAllClaims(String token) //Obtenemos toda la informacion del token
    {
        return Jwts.parser()
                .verifyWith(getSignatureKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public <T> T getClaim(String token, Function<Claims,T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public SecretKey getSignatureKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
