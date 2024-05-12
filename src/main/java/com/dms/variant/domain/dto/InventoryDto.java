package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {
    private Integer id;

    private String item_name;

    private String sku;

    private String kind;

    private String type;

    private String category;

    private String sub_category;

    private String item_group;

    private String origin;

    private String price_range;

    private String pref_supplier;

    private String status;

    private String notes;
}
