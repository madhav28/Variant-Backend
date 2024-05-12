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
@Table(name = "tax_codes")
public class TaxCodesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_codes_id_seq")
    private Long id;

    private String so;

    private String code;

    private String label;

    private String current_rate;

    private String rate;

    private String city;

    private String county;

    private String state;

    private String zip;

    private String pur_tax;

    private String sal_tax;

    private String use_tax;

}
