package com.dms.variant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "packages")
public class PackagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packages_id_seq")
    private Long id;

    private String name;

    private String description;

    private String products_having;

    private String used_in;

    private String last_used;

}
