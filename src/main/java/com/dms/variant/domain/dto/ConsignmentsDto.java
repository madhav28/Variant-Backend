package com.dms.variant.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class ConsignmentsDto {

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
