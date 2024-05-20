package com.dms.variant.controllers.user;

import com.dms.variant.domain.dto.UserDto;
import com.dms.variant.exceptions.InvalidUserInput;
import com.dms.variant.services.UserService;
import com.dms.variant.utils.ResponseEntityBuildUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public SignupController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> signup(@RequestBody UserDto userDto) {
        validateSignupData(userDto);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.save(userDto.toUserEntity());
        return ResponseEntityBuildUtil.getResponseEntity(true, null, HttpStatus.CREATED);
    }

    private void validateSignupData(UserDto userDto) {
        if(StringUtils.isEmpty(userDto.getFirstName())) {
            throw new InvalidUserInput("First Name cannot be null or empty");
        }
        if(StringUtils.isEmpty(userDto.getLastName())) {
            throw new InvalidUserInput("Last Name cannot be null or empty");
        }
    }
}
