package com.ebc.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    public interface UserView {
        interface RegistrationPost {
        }

        interface UserPut {
        }

        interface PasswordPut {
        }

        interface ImagePut {
        }
    }

    @JsonView(UserView.RegistrationPost.class)
    private final String username;
    @JsonView(UserView.RegistrationPost.class)
    private final String email;
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private final String password;
    @JsonView(UserView.PasswordPut.class)
    private final String oldPassword;
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private final String fullname;
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private final String phoneNumber;
    @JsonView({UserView.RegistrationPost.class, UserView.UserPut.class})
    private final String cpf;
    @JsonView(UserView.ImagePut.class)
    private final String imageUrl;
}
