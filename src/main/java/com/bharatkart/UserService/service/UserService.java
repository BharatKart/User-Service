package com.bharatkart.UserService.service;

import com.bharatkart.UserService.model.dto.UserRequestDto;
import com.bharatkart.UserService.model.dto.UserResponseDto;
import com.bharatkart.UserService.model.entity.Users;
import com.bharatkart.UserService.utility.ApiResponse;

public interface UserService {

 ApiResponse<UserResponseDto> userSignUp(UserRequestDto users);


}
