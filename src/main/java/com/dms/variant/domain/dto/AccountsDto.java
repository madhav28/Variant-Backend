package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsDto {

    private Long id;

    private String number;

    private String name;

    private String alternate_number;

    private String alternate_name;

    private String type;

    private String sub_type;

    private String special_type;

    private String sub_account_of;

    private String balance;

    private String status;

}
