package com.bharatkart.UserService.model.dto;


import com.bharatkart.UserService.model.entity.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserSignUpRequestDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "UsernameType is required")
    private Users.UsernameType usernameType;  // enum

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;
}
