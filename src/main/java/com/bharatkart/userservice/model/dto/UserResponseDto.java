package com.bharatkart.userservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String usernameType;
    private String firstName;
    private String lastName;
    private String status;
}
