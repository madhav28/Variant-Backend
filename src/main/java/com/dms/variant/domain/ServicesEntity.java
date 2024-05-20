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
@Table(name = "services")
public class ServicesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "services_id_seq")
    private Long id;

    private String name;

    private String sku;

    private String category;

    private String type;

    private String services_group;

    private Double price1;

    private String units;

    private String price_range;

    private String pref_vendor;

    private String notes;

    private String status;

    private String usage;
}
