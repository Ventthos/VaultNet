package com.ventthos.Vaultnet.domain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;
    private String paternalLastName;
    private String maternalLastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String image;
}
