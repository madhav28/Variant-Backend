package com.dms.variant.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tax_authorities")
public class TaxAuthoritiesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_authorities_id_seq")
    private Long id;

    private String name;

    private String code;

    private String city;

    private String state;

    private String zip;

    private String tax_no;

}
