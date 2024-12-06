package com.Authentication.JWTAuthentication.service;

import com.Authentication.JWTAuthentication.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService
{
    private String secretKey = "55f884b58b2d5b2ffc9487b619e71aaae5e3ea7779784fc827274ae17b805ebd";


    public String extractUsername(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }
//
//    public boolean isValid(String token, UserDetails userDetails)
//    {
//        String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token)
//    {
//        return extractExpiration(token).before(new Date());
//    }
//
//    private Date extractExpiration(String token)
//    {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
    public <T> T extractClaim(String token, Function<Claims,T> resolver)
    {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateToken(User user)
    {
        String token = Jwts
                .builder()
                .subject(user.getUserName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSignInKey()).compact();

        return token;
    }

    private SecretKey getSignInKey()
    {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
