package com.smartorder.api.models;

import com.smartorder.api.models.enums.ClientStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "document")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client extends BaseEntity {

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(length = 20)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;

    @Column(nullable = false)
    private Integer score = 0;

    public Client(String name, String email, String cpf) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Noem do cliente é obrigatorio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email do cliente é obrigatorio.");
        }

        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }

}
