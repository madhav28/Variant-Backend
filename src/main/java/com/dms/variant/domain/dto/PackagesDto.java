package com.dms.variant.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PackagesDto {

    private Long id;

    private String name;

    private String description;

    private String products_having;

    private String used_in;

    private String last_used;

}
