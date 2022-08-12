package com.ebc.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private final String username;
    private final String email;
    private final String password;
    private final String oldPassword;
    private final String fullname;
    private final String phoneNumber;
    private final String cpf;
    private final String imageUrl;
}
