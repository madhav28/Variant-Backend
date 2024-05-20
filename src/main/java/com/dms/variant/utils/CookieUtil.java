package com.dms.variant.utils;

import org.springframework.beans.factory.annotation.Value;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieUtil {

    @Value("${spring.security.jwt.expiryInSec}")
    private static int expiryTime;

    //Hard-coded cookie name. Make sure it is in sync with NavbarComponent.
    private static final String COOKIE_NAME = "JWT";
    private static final String COOKIE_PATH = "/";

    public static Cookie createLoginCookieFromToken(String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setPath(COOKIE_PATH);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(expiryTime*1000);
        return cookie;
    }

    public static Cookie readAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(COOKIE_NAME)) {
                return cookie;
            }
        }
        return null;
    }
}
