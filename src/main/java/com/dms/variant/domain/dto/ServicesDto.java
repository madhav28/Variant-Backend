package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicesDto {

    private Long id;

    private String name;

    private String sku;

    private String category;

    private String type;

    private String services_group;

    private Double price1;

    private String units;

    private String price_range;

    private String pref_vendor;

    private String notes;

    private String status;

    private String usage;
}
