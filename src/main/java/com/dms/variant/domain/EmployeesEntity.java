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
@Table(name = "employees")
public class EmployeesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_seq")
    private Long id;

    private String name;

    private String username;

    private Long logins_last_30_days;

    private String user_role;

    private String classification;

}
