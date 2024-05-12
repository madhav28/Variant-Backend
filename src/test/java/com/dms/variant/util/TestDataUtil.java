package com.dms.variant.util;

import com.dms.variant.domain.*;

public class TestDataUtil {
    public static InventoryEntity createTestInventoryA() {
        return InventoryEntity.builder()
                .item_name("12X24X1.5CM-STONE TILE-ANATOLIA-STRADA MIST MARBLE-VEINCUT-HONED AKA: White Wood Vein, Vena Grigio")
                .sku("")
                .kind("Custom")
                .type("Tile")
                .category("Marble")
                .sub_category("")
                .item_group("")
                .origin("China")
                .price_range("Group 1")
                .pref_supplier("")
                .status("")
                .notes("")
                .build();
    }

    public static InventoryEntity createTestInventoryB() {
        return InventoryEntity.builder()
                .item_name("12X24X1.5CM-STONE TILE-ANATOLIA-STRADA MIST MARBLE-VEINCUT-HONED AKA: White Wood Vein, Vena Grigio")
                .sku("")
                .kind("Custom")
                .type("Tile")
                .category("Marble")
                .sub_category("")
                .item_group("")
                .origin("China")
                .price_range("Group 1")
                .pref_supplier("")
                .status("")
                .notes("")
                .build();
    }

    public static InventoryEntity createTestInventoryC() {
        return InventoryEntity.builder()
                .item_name("12X24X1.5CM-STONE TILE-ANATOLIA-STRADA MIST MARBLE-VEINCUT-HONED AKA: White Wood Vein, Vena Grigio")
                .sku("")
                .kind("Custom")
                .type("Tile")
                .category("Marble")
                .sub_category("")
                .item_group("")
                .origin("China")
                .price_range("Group 1")
                .pref_supplier("")
                .status("")
                .notes("")
                .build();
    }

    public static ServicesEntity createTestServicesA() {
        return ServicesEntity.builder()
                .name("A Frame")
                .sku(null)
                .category(null)
                .type(null)
                .price1(180.0)
                .units("EA")
                .price_range(null)
                .pref_vendor(null)
                .notes(null)
                .status(null)
                .usage("SO, customer credit memo")
                .build();
    }

    public static ServicesEntity createTestServicesB() {
        return ServicesEntity.builder()
                .name("A Frame")
                .sku(null)
                .category(null)
                .type(null)
                .price1(180.0)
                .units("EA")
                .price_range(null)
                .pref_vendor(null)
                .notes(null)
                .status(null)
                .usage("SO, customer credit memo")
                .build();
    }

    public static PackagesEntity createTestPackagesA() {
        return PackagesEntity.builder()
                .description("test")
                .build();
    }

    public static PackagesEntity createTestPackagesB() {
        return PackagesEntity.builder()
                .description("test")
                .build();
    }

    public static CustomersEntity createTestCustomersA() {
        return CustomersEntity.builder()
                .name("test")
                .build();
    }

    public static SuppliersEntity createTestSuppliersA() {
        return SuppliersEntity.builder()
                .name("test")
                .build();
    }

    public static VendorsEntity createTestVendorsA() {
        return VendorsEntity.builder()
                .name("test")
                .build();
    }

    public static AssociatesEntity createTestAssociatesA() {
        return AssociatesEntity.builder()
                .name("test")
                .build();
    }

    public static EmployeesEntity createTestEmployeesA() {
        return EmployeesEntity.builder()
                .name("test")
                .build();
    }

    public static ConsignmentsEntity createTestConsignmentsA() {
        return ConsignmentsEntity.builder()
                .location("test")
                .build();
    }

    public static AccountsEntity createTestAccountsA() {
        return AccountsEntity.builder()
                .name("test")
                .build();
    }

    public static TaxCodesEntity createTestTaxCodesA() {
        return TaxCodesEntity.builder()
                .so("test")
                .build();
    }

    public static TaxAuthoritiesEntity createTestTaxAuthoritiesA() {
        return TaxAuthoritiesEntity.builder()
                .name("test")
                .build();
    }
}
