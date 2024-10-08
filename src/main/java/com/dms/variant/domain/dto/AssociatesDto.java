package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssociatesDto {

    private Long id;

    private String name;

    private String contact;

    private String code;

    private String route;

    private String type;

    private String location;

    private String sales_rep;

    private String sec_sales_rep;

    private String address;

    private String suite;

    private String city;

    private String state;

    private Integer zip;

    private String country;

    private String primary_phone;

    private String secondary_phone;

    private String fax;

    private String mobile;

    private String email;

    private String accounting_email;

    private String website;

    private String internal_notes;

    private String status;

}
