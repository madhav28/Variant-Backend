package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorsDto {

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
