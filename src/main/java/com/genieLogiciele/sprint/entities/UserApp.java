package com.genieLogiciele.sprint.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // Stratégie d'héritage pour toute la hiérarchie
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UserApp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @NotBlank(message = "Le champ email est vide")
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Le champ password est vide")
    private String password;

    @Pattern(regexp = "^(?:\\+212|0)([567])\\d{8}$", message = "ce numero de telephone est invalid")
    private String telephone;
    @Size(min = 4, message = "le champ Role est inferieur a 4 caracteres")
    private String role;// "Devloppeur " , "Admin " , "Product Owner" ,


}

