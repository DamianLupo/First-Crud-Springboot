package com.application.rest.Entities;


import com.application.rest.Entities.Role.UserRol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "usuarios")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(name = "nombre_usuario")
    private String username;
    @Column(name = "contrasenia")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = UserRol.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<UserRol> rols;
}
