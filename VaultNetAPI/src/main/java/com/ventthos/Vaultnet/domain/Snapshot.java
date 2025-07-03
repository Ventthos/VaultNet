package com.ventthos.Vaultnet.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "snapshots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Snapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snapshotId;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    private LocalDate date;
}
