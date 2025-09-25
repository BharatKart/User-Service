package com.bharatkart.UserService.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;           // HTTP-like code (200, 400, 500)
    private Status status;      // SUCCESS / ERROR
    private String message;     // Human-readable message
    private T data;             // Generic type-safe payload

    public enum Status {
        SUCCESS,
        ERROR
    }
}
