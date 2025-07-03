package com.ventthos.Vaultnet.domain;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String paternalLastName;

    @Column(length = 100)
    private String maternalLastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Change> changes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBusiness> businesses;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Business> ownerships;

}
