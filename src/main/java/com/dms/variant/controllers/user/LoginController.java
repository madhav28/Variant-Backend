package com.dms.variant.controllers.user;

import com.dms.variant.domain.UserEntity;
import com.dms.variant.domain.dto.UserDto;
import com.dms.variant.exceptions.InvalidUserInput;
import com.dms.variant.services.JwtTokenService;
import com.dms.variant.services.UserService;
import com.dms.variant.utils.CookieUtil;
import com.dms.variant.utils.ResponseEntityBuildUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

@RestController
public class LoginController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto, HttpServletResponse response) {
        validateLoginCredentials(userDto);
        String token = jwtTokenService.generateToken(userDto.getUsername());
        Cookie cookie = CookieUtil.createLoginCookieFromToken(token);
        response.addCookie(cookie);
        return ResponseEntityBuildUtil.getResponseEntity(true, null, HttpStatus.CREATED);
    }

    private void validateLoginCredentials(UserDto userDto) {
        if(!StringUtils.hasText(userDto.getUsername()) || !StringUtils.hasText(userDto.getPassword())) {
            throw new InvalidUserInput("Username/Password cannot be empty");
        }

        Optional<UserEntity> user = userService.findByUsername(userDto.getUsername());
        if(user.isEmpty()) {
            throw new InvalidUserInput("Invalid username or password");
        }
        if(!passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            throw new InvalidUserInput("Invalid username or password");
        }
    }


}
