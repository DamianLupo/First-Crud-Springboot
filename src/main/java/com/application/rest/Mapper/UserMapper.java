package com.application.rest.Mapper;

import com.application.rest.Controllers.DTO.UserDTO;
import com.application.rest.Entities.Role.ERole;
import com.application.rest.Entities.Role.UserRol;
import com.application.rest.Entities.UserEntity;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(UserDTO userDTO);
    default Set<UserRol> mapRoles(Set<String> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(roleName -> UserRol.builder()
                        .name(ERole.valueOf(roleName))
                        .build())
                .collect(Collectors.toSet());
    }
}
