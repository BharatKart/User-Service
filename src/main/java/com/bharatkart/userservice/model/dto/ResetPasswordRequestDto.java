package com.bharatkart.userservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String newPassword;

    @NotBlank(message = "OTP is required")
    private String otp;

}
