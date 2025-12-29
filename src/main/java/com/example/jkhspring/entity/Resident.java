package com.example.jkhspring.entity;

import com.example.jkhspring.searchable.Searchable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "residents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resident implements Searchable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long residentId;

    @Column(nullable = false)
    private String name;

    private String phone;

    private String email;

    @Override
    public String toString() {
        return "Resident: id=" + residentId +
                ", name='" + name + '\'' +
                (phone == null ? "" : ", phone='" + phone + '\'') +
                (email == null ? "" : ", email='" + email + '\'');
    }
}
