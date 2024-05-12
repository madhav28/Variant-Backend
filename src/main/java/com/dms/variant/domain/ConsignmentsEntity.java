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
@Table(name = "consignments")
public class ConsignmentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consignments_id_seq")
    private Long id;

    private String location;

    private String parent_location;

    private String setup_date;

    private String type;

    private String billing_address;

    private String shipping_address;

    private String phones;

    private String sales_rep;

    private String price_level;

    private String pmt_terms;

    private String internal_notes;

    private String status;

}
