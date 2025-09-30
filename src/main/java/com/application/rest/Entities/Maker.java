package com.application.rest.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "fabricante")
public class Maker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre")
    private String name;
    @OneToMany(mappedBy = "maker", cascade = CascadeType.ALL, fetch = FetchType.LAZY , orphanRemoval = true)
    @JsonIgnore
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();
    //Mapped by indica que el lado dueño esta en el otro lado
    //Cascade indica que los cambios hechos en la padre (maker) se aplican a los hijos (product
    //FetchLazy los hijos no se traen automaticamente, solo se cargan cuando los pido
    //OrphanRemoval = true indica que si quito un hijo de la relacion del apdre, tambien lo borra de la bdd
}
