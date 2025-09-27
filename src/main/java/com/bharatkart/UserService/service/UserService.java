package com.bharatkart.UserService.service;

import com.bharatkart.UserService.model.dto.UserLoginRequestDto;
import com.bharatkart.UserService.model.dto.UserResponseDto;
import com.bharatkart.UserService.model.dto.UserSignUpRequestDto;
import com.bharatkart.UserService.model.dto.UserUpdateRequestDto;
import com.bharatkart.UserService.utility.ApiResponse;

public interface UserService {

 ApiResponse<UserResponseDto> userSignUp(UserSignUpRequestDto userSignUpRequestDto);
 ApiResponse<String> userLoginWithPwd(UserLoginRequestDto userLoginRequestDto);

 ApiResponse<UserResponseDto> getCurrentUser(String name);

 ApiResponse<UserResponseDto> updateUSerDetails(UserUpdateRequestDto userUpdateRequestDto, String name);

 ApiResponse<String> deActivateUser(String name);
}
