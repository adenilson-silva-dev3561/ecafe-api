package eCafe.API.customers.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @CPF(message = "CPF inválido")
    @Column(nullable = true, unique = true)
    private String cpf;

    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;

    private boolean active;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updateAt;

    @Column(name = "keycloak_user_id", nullable = false, unique = true)
    private String keycloakUserId;
}