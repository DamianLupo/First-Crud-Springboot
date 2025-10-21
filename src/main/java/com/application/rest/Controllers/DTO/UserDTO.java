package com.application.rest.Controllers.DTO;

import com.application.rest.Entities.Role.UserRol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @Email(message = "Email no valido")
    @NotNull(message = "El email no puede estar vacio")
    @Size(max = 50, message = "El email no puede tener mas de 50 caracteres")
    private String email;
    @NotNull(message = "El nombre de usuario no puede estar vacio")
    @Size(max = 50, message = "El nombre de usuario no puede tener mas de 50 caracteres")
    private String username;
    @NotNull(message = "La contraseña no puede estar vacia")
    @Size(max = 50, message = "La contraseña no puede tener mas de 50 caracteres")
    private String password;
    Set<String> rols;
}
