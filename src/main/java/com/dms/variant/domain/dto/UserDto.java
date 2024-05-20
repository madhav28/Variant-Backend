package com.dms.variant.domain.dto;

import com.dms.variant.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String phone;

    private Boolean isUserVerified;

    private Date lastLoginDate;

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .email(email)
                .phone(phone)
                .isUserVerified(isUserVerified)
                .lastLoginDate(lastLoginDate)
                .build();
    }

}
