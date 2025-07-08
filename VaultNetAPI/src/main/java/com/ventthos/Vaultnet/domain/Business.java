package com.ventthos.Vaultnet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "businesses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    @Column(length = 100)
    private String name;

    private String logoUrl;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Snapshot> snapshots = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Unit> units = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
    
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<UserBusiness> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

}
