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
@Table(name = "suppliers")
public class SuppliersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suppliers_id_seq")
    private Long id;

    private String name;

    private String parent_supplier;

    private String currency;

    private String type;

    private String address;

    private String phones;

    private String location;

    private String pmt_terms;

    private String internal_notes;

    private String status;

}
