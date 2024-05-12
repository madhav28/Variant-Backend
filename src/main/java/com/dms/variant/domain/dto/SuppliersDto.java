package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuppliersDto {

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
