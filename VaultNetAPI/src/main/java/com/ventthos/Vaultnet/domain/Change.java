package com.ventthos.Vaultnet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "changes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Change {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long changeId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String changes;
}
