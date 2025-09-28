package com.bharatkart.userservice.service;

import com.bharatkart.userservice.Repository.PasswordResetTokenRepository;
import com.bharatkart.userservice.Repository.UsersRepository;
import com.bharatkart.userservice.exception.UserNotFoundException;
import com.bharatkart.userservice.model.dto.*;
import com.bharatkart.userservice.model.entity.PasswordResetToken;
import com.bharatkart.userservice.model.entity.Users;
import com.bharatkart.userservice.exception.UserAlreadyExistsException;
import com.bharatkart.userservice.utility.ApiResponse;
import com.bharatkart.userservice.utility.Constants;
import com.bharatkart.userservice.utility.JwtUtil;
import com.bharatkart.userservice.utility.OTP;
import com.bharatkart.userservice.utility.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UsersRepository usersRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final OTP otp;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    @Transactional
    public ApiResponse<UserResponseDto> userSignUp(UserSignUpRequestDto userSignUpRequestDto) {
        log.info("Attempting to register user with username: {}", userSignUpRequestDto.getUsername());
        if(usersRepository.findByUsername(userSignUpRequestDto.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("User already exists with username: " + userSignUpRequestDto.getUsername());
        }

        //convert dto to entity
        Users newUser = userMapper.toEntity(userSignUpRequestDto);
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

    @Override
    public ApiResponse<String> userLoginWithPwd(UserLoginRequestDto userLoginRequestDto) {
        log.info("Attempting to Login user with username: {}", userLoginRequestDto.getUsername());
        Users users=usersRepository.findByUsername(userLoginRequestDto.getUsername()).orElseThrow(()-> new UserNotFoundException(Constants.INVALID_USER_PASSWORD));
        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(),users.getPassword())){
            throw new BadCredentialsException(Constants.INVALID_USER_PASSWORD);
        }
    String token=jwtUtil.generateToken(users.getUsername());

        return ApiResponse.<String>builder()
                .code(201)
                .status(ApiResponse.Status.SUCCESS)
                .message("User Login successfully")
                .data(token)
                .build();
    }

    @Override
    public ApiResponse<UserResponseDto> getCurrentUser(String userName) {
       Users user= usersRepository.findByUsername(userName).orElseThrow(()-> new UsernameNotFoundException(Constants.USER_NOT_FOUND));

        return ApiResponse.<UserResponseDto>builder()
                .code(200)
                .status(ApiResponse.Status.SUCCESS)
                .message("User Details Fetched successfully")
                .data(userMapper.userResponseToDto(user))
                .build();


    }

    @Override
    public ApiResponse<UserResponseDto> updateUSerDetails(UserUpdateRequestDto userUpdateRequestDto, String userName) {
        Users user= usersRepository.findByUsername(userName).orElseThrow(()-> new UsernameNotFoundException(Constants.USER_NOT_FOUND));

        if(userUpdateRequestDto.getFirstName()!=null) {
            user.setFirstName(userUpdateRequestDto.getFirstName());
        }
        if(userUpdateRequestDto.getLastName()!=null) {
            user.setLastName(userUpdateRequestDto.getLastName());
        }
        if(userUpdateRequestDto.getPassword()!=null) {
            user.setPassword(passwordEncoder.encode(userUpdateRequestDto.getPassword()));
        }

        Users updatedUser=usersRepository.save(user);

         return ApiResponse.<UserResponseDto>builder()
                .code(200)
                .status(ApiResponse.Status.SUCCESS)
                .message("User Details Fetched successfully")
                .data(userMapper.userResponseToDto(updatedUser))
                .build();
    }

    @Override
    public ApiResponse<String> deActivateUser(String userName) {
        Users user= usersRepository.findByUsername(userName).orElseThrow(()-> new UsernameNotFoundException(Constants.USER_NOT_FOUND));

        user.setStatus(Users.Status.INACTIVE);

        usersRepository.save(user);

        return ApiResponse.<String>builder()
                .code(200)
                .status(ApiResponse.Status.SUCCESS)
                .message("User deactivated successfully")
                .build();
    }

    @Override
    public ApiResponse<String> forgotPassword(String userName) {
        Users user= usersRepository.findByUsername(userName).orElseThrow(()-> new UsernameNotFoundException(Constants.USER_NOT_FOUND));
        String generateOTP=otp.generateOTP();

        log.info("Generated OTP for {}: {}", user.getUsername(), generateOTP);
        PasswordResetToken passwordResetToken=new PasswordResetToken();
        passwordResetToken.setUsername(user.getUsername());
        passwordResetToken.setOtp(generateOTP);
        passwordResetToken.setExpiryTime(LocalDateTime.now().plusMinutes(Constants.OTP_TIME_EXPIRY_TIME));
        passwordResetToken.setUsed(false);
        passwordResetTokenRepository.save(passwordResetToken);

        return ApiResponse.<String>builder()
                .code(200)
                .status(ApiResponse.Status.SUCCESS)
                .message("OTP Sent Successfully ")
                .data(generateOTP)
                .build();

    }

    @Override
    public ApiResponse<String> resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
       PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByUsernameAndOtpAndUsedFalse(resetPasswordRequestDto.getUsername(),resetPasswordRequestDto.getOtp()).orElseThrow(()->new RuntimeException("Invalid or Expired OTP"));

       if(passwordResetToken.getExpiryTime().isBefore(LocalDateTime.now())){
           throw new RuntimeException("OTP has Expired");
       }
        Users user= usersRepository.findByUsername(resetPasswordRequestDto.getUsername()).orElseThrow(()-> new UsernameNotFoundException(Constants.USER_NOT_FOUND));

       user.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
       usersRepository.save(user);

       passwordResetToken.setUsed(true);
       passwordResetTokenRepository.save(passwordResetToken);

        return ApiResponse.<String>builder()
                .code(200)
                .status(ApiResponse.Status.SUCCESS)
                .message("Password reset successfully")
                .build();

    }


}
