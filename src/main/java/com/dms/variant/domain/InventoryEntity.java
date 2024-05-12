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
@Table(name = "inventory")
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_seq")
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
