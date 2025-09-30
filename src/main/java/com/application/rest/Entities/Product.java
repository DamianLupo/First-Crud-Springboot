package com.application.rest.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "producto")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre")
    private String name;
    @Column(name="precio")
    private BigDecimal price; //Similar a un double pero mas preciso para trabajar con plata

    @ManyToOne
    @JoinColumn (name = "id_fabricante", nullable = false)
    @JsonBackReference
    private Maker maker;
}
