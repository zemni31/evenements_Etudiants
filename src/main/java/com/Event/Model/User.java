package com.Event.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{validation.name.notblank}")
    @Size(min = 2, max = 50, message = "{validation.name.size}")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "{validation.email.notblank}")
    @Email(message = "{validation.email.invalid}")
    @Column(nullable = false, unique = true)
    private String email;

    // Le mot de passe stocké sera toujours le hash BCrypt, jamais en clair
    @Column(nullable = false)
    private String password;
}