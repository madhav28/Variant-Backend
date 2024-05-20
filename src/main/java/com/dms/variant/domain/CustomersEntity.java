package com.dms.variant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class CustomersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_id_seq")
    private Long id;

    private String name;

    private String print_name;

    private String code;

    private String origin_system_reference;

    private String contact_name;

    private String parent_customer;

    private String route;

    private String type;

    private String address;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String county;

    private String country;

    private String phone1;

    private String phone2;

    private String mobile;

    private String email;

    private String accounting_email;

    private String parent_location;

    private String sales_person1;

    private String sales_person2;

    private String project_manager;

    private String price_level1;

    private String price_level2;

    private String payment_terms;

    private String sales_tax;

    private String status;

    private String lock_alert;

    private String notes;

    private String delivery_instruction;

}
