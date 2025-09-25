package com.bharatkart.UserService.service;

import com.bharatkart.UserService.Repository.UsersRepository;
import com.bharatkart.UserService.model.dto.UserRequestDto;
import com.bharatkart.UserService.model.dto.UserResponseDto;
import com.bharatkart.UserService.model.entity.Users;
import com.bharatkart.UserService.exception.UserAlreadyExistsException;
import com.bharatkart.UserService.utility.ApiResponse;
import com.bharatkart.UserService.utility.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UsersRepository usersRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ApiResponse<UserResponseDto> userSignUp(UserRequestDto userRequestDto) {
        log.info("Attempting to register user with username: {}", userRequestDto.getUsername());
        if(usersRepository.findByUsername(userRequestDto.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("User already exists with username: " + userRequestDto.getUsername());
        }

        //convert dto to entity
        Users newUser = userMapper.toEntity(userRequestDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));


        Users savedUser = usersRepository.save(newUser);

        // Return standard response
        return ApiResponse.<UserResponseDto>builder()
                .code(201)
                .status(ApiResponse.Status.SUCCESS)
                .message("User registered successfully")
                .data(userMapper.toDto(savedUser))
                .build();
    }
}
