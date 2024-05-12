package com.dms.variant.repositories;

import com.dms.variant.domain.InventoryEntity;
import com.dms.variant.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InventoryRepositoryIntegrationTests {

    private InventoryRepository underTest;

    @Autowired
    public InventoryRepositoryIntegrationTests(InventoryRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatInventoryCanBeCreatedAndRecalled() {
        InventoryEntity inventory = TestDataUtil.createTestInventoryA();
        underTest.save(inventory);
        Optional<InventoryEntity> result = underTest.findById(inventory.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(inventory);
    }

    @Test
    public void testThatMultipleInventoriesCanBeCreatedAndRecalled() {
        InventoryEntity inventoryA = TestDataUtil.createTestInventoryA();
        underTest.save(inventoryA);
        InventoryEntity inventoryB = TestDataUtil.createTestInventoryB();
        underTest.save(inventoryB);
        InventoryEntity inventoryC = TestDataUtil.createTestInventoryC();
        underTest.save(inventoryC);

        Iterable<InventoryEntity> result = underTest.findAll();
        assertThat(result).hasSize(3).containsExactly(inventoryA, inventoryB, inventoryC);
    }

    @Test
    public void testThatInventoryCanBeUpdated() {
        InventoryEntity inventory = TestDataUtil.createTestInventoryA();
        underTest.save(inventory);
        inventory.setItem_name("UPDATED");
        underTest.save(inventory);
        Optional<InventoryEntity> result = underTest.findById(inventory.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(inventory);
    }

    @Test
    public void testThatInventoryCanBeDeleted() {
        InventoryEntity inventory = TestDataUtil.createTestInventoryA();
        underTest.save(inventory);
        underTest.deleteById(inventory.getId());
        Optional<InventoryEntity> result = underTest.findById(inventory.getId());
        assertThat(result).isEmpty();
    }


}
