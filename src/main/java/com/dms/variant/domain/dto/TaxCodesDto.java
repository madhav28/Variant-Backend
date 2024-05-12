package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxCodesDto {

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
