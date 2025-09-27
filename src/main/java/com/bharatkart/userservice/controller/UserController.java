package com.bharatkart.userservice.controller;

import com.bharatkart.userservice.model.dto.UserLoginRequestDto;
import com.bharatkart.userservice.model.dto.UserResponseDto;
import com.bharatkart.userservice.model.dto.UserSignUpRequestDto;
import com.bharatkart.userservice.model.dto.UserUpdateRequestDto;
import com.bharatkart.userservice.service.UserService;
import com.bharatkart.userservice.utility.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

   private final UserService userService;

    @PostMapping("/singUp")
    public ApiResponse<UserResponseDto> userSignUp(@Valid  @RequestBody UserSignUpRequestDto userRequestDto){
        return userService.userSignUp(userRequestDto);
    }

    @PostMapping("/login")
    public ApiResponse<String> loginUser(
            @Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
       return userService.userLoginWithPwd(userLoginRequestDto);
    }

    @GetMapping("/me")
    public ApiResponse<UserResponseDto> getCurrentUser(Authentication authentication){
        return userService.getCurrentUser(authentication.getName());
    }

    @PutMapping("/me")
    public ApiResponse<UserResponseDto> updateUSerDetails(Authentication authentication,@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return userService.updateUSerDetails(userUpdateRequestDto,authentication.getName());
    }


    @PutMapping("/deActivate")
    public ApiResponse<String> deActivateUser(Authentication authentication){
        return userService.deActivateUser(authentication.getName());
    }

}
