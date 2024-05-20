package com.dms.variant.services;


import io.jsonwebtoken.Claims;

public interface JwtTokenService {

    String generateToken(String username);

    Claims extractClaims(String token);
}
