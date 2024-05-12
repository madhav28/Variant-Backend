package com.dms.variant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeesDto {

    private Long id;

    private String name;

    private String username;

    private Long logins_last_30_days;

    private String user_role;

    private String classification;

}
