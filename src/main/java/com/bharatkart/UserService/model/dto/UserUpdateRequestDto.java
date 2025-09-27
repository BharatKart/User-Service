package com.bharatkart.UserService.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateRequestDto {

    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must be up to 50 characters")
    private String lastName;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}

