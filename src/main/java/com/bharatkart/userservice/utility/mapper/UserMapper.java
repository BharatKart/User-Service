package com.bharatkart.userservice.utility.mapper;

import com.bharatkart.userservice.model.dto.UserResponseDto;
import com.bharatkart.userservice.model.dto.UserSignUpRequestDto;
import com.bharatkart.userservice.model.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // ignore unmapped fields (like audit fields)
)
public interface UserMapper {

    // Request DTO -> Entity
    Users toEntity(UserSignUpRequestDto dto);

    // Entity -> Response DTO
    UserResponseDto toDto(Users users);

    UserResponseDto userResponseToDto(Users users);
}
