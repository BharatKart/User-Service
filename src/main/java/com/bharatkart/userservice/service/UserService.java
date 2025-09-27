package com.bharatkart.userservice.service;

import com.bharatkart.userservice.model.dto.UserLoginRequestDto;
import com.bharatkart.userservice.model.dto.UserResponseDto;
import com.bharatkart.userservice.model.dto.UserSignUpRequestDto;
import com.bharatkart.userservice.model.dto.UserUpdateRequestDto;
import com.bharatkart.userservice.utility.ApiResponse;

public interface UserService {

 ApiResponse<UserResponseDto> userSignUp(UserSignUpRequestDto userSignUpRequestDto);
 ApiResponse<String> userLoginWithPwd(UserLoginRequestDto userLoginRequestDto);

 ApiResponse<UserResponseDto> getCurrentUser(String name);

 ApiResponse<UserResponseDto> updateUSerDetails(UserUpdateRequestDto userUpdateRequestDto, String name);

 ApiResponse<String> deActivateUser(String name);
}
