package com.application.rest.Controllers;

import com.application.rest.Controllers.DTO.UserDTO;
import com.application.rest.Entities.Role.ERole;
import com.application.rest.Entities.Role.UserRol;
import com.application.rest.Exceptions.BadRequestException;
import com.application.rest.Mapper.UserMapper;
import com.application.rest.Repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/")
    public ResponseEntity<Void> save(@Valid @RequestBody UserDTO userDTO)
    {
        Set<UserRol> rols = userDTO.getRols().stream()
                .map(roleName -> UserRol.builder()
                        .name(ERole.valueOf(roleName))
                        .build())
                .collect(Collectors.toSet());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userMapper.toEntity(userDTO));
        return ResponseEntity.created(URI.create("/api/user/")).build();
    }
    @DeleteMapping("/")
    public ResponseEntity<String> deleteById(@PathVariable Long id)
    {
        if(id==null)
        {
            throw new BadRequestException("El id no puede ser nulo");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Registro eliminado");
    }
}
