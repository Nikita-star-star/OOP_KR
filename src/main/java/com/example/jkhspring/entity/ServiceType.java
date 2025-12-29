package com.example.jkhspring.entity;

import com.example.jkhspring.searchable.Searchable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "service_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceType implements Searchable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceTypeId;

    @Column(nullable = false)
    private String name;

    /**
     * Стоимость услуги (по аналогии с ServiceE.price)
     */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    /**
     * Длительность в минутах (по аналогии с ServiceE.duration)
     */
    @Column(nullable = false)
    private Integer durationMinutes;

    @Override
    public String toString() {
        return "ServiceType: id=" + serviceTypeId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", durationMinutes=" + durationMinutes;
    }
}
