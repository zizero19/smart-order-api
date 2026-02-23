package com.smartorder.api.models;

import com.smartorder.api.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "cpf")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseEntity {

    @Column(nullable = false, length = 120)
    public String name;

    @Column(nullable = false, length = 150)
    public String email;

    @Column(length = 20)
    public String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public ClientStatus status = ClientStatus.ACTIVE;

    @Column(nullable = false)
    public Integer score = 0;

    public Client(String name, String email, String cpf) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatorio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email do cliente é obrigatorio.");
        }

        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

}
