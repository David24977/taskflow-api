package com.david.taskflow_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 50, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private Role role;
    //User permitido
    @Column(nullable = false)
    private Boolean enabled = true;
    //Dice si una cuenta está bloqueada o no,
    // lo debemos poner para cumplir con la interfaz UserDetail de security
    @Column(nullable = false)
    private boolean accountNonLocked = true;
}
