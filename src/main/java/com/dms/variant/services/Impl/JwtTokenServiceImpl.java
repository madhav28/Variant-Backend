package com.dms.variant.services.Impl;

import com.dms.variant.services.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    @Value("${spring.security.jwt.expiryInSec}")
    private long expiryTime;

    @Override
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiryTime*1000))
                .signWith(SignatureAlgorithm.HS512, getHmacShaSecretKey())
                .compact();
    }

    @Override
    public Claims extractClaims(String token) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(getHmacShaSecretKey())
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    private Key getHmacShaSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
