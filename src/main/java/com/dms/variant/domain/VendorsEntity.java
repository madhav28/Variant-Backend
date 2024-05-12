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
@Table(name = "vendors")
public class VendorsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendors_id_seq")
    private Long id;

    private String name;

    private String print_name;

    private String type;

    private String address;

    private String phones;

    private String location;

    private String pmt_terms;

    private String account;

    private String notes;

    private String status;

}
