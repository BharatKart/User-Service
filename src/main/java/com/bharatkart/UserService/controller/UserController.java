package com.bharatkart.UserService.controller;

import com.bharatkart.UserService.model.dto.UserRequestDto;
import com.bharatkart.UserService.model.dto.UserResponseDto;
import com.bharatkart.UserService.service.UserService;
import com.bharatkart.UserService.utility.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

   private final UserService userService;

    @PostMapping("/singUp")
    public ApiResponse<UserResponseDto> userSignUp(@Valid  @RequestBody UserRequestDto userRequestDto){
        return userService.userSignUp(userRequestDto);
    }
}
