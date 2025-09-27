package com.bharatkart.userservice.exception;

import com.bharatkart.userservice.utility.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(409)
                .status(ApiResponse.Status.ERROR)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(UserNotFoundException ex) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(404)
                .status(ApiResponse.Status.ERROR)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleEnumErrors(HttpMessageNotReadableException ex) {
        String message = "Invalid request";

        if (ex.getCause() instanceof InvalidFormatException invalidEx &&
                invalidEx.getTargetType().isEnum()) {
            message = "Invalid value for enum " + invalidEx.getTargetType().getSimpleName() +
                    ". Allowed values: " + Arrays.toString(invalidEx.getTargetType().getEnumConstants());
        }

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(400)
                .status(ApiResponse.Status.ERROR)
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneric(Exception ex) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(500)
                .status(ApiResponse.Status.ERROR)
                .message("Internal server error: " + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Get the first field error message
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Validation error");

        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(400)
                .status(ApiResponse.Status.ERROR)
                .message(errorMessage)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .code(401)
                .status(ApiResponse.Status.ERROR)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

    }
    }
