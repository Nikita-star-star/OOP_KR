package com.example.jkhspring.entity;

import com.example.jkhspring.searchable.Searchable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Worker implements Searchable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    @Column(nullable = false)
    private String name;

    /**
     * Роль/специализация (по аналогии со Specialist.specialization)
     */
    private String role;

    private double rating;

    @Override
    public String toString() {
        return "Worker: id=" + workerId +
                ", name='" + name + '\'' +
                (role == null ? "" : ", role='" + role + '\'') +
                ", rating=" + rating;
    }
}
