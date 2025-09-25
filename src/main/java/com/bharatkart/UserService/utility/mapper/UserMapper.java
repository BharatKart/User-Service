package com.bharatkart.UserService.utility.mapper;

import com.bharatkart.UserService.model.dto.UserRequestDto;
import com.bharatkart.UserService.model.dto.UserResponseDto;
import com.bharatkart.UserService.model.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // ignore unmapped fields (like audit fields)
)
public interface UserMapper {

    // Request DTO -> Entity
    Users toEntity(UserRequestDto dto);

    // Entity -> Response DTO
    UserResponseDto toDto(Users users);
}
