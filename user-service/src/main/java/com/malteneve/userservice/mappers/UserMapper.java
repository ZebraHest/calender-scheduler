package com.malteneve.userservice.mappers;

import com.malteneve.userservice.domain.User;
import com.malteneve.userservice.dto.SignUpDto;
import com.malteneve.userservice.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);


}
