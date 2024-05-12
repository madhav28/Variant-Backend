package com.dms.variant.controllers;

import com.dms.variant.domain.InventoryEntity;
import com.dms.variant.services.InventoryService;
import com.dms.variant.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class InventoryControllerIntegrationTests {

    private InventoryService inventoryService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public InventoryControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, InventoryService inventoryService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.inventoryService = inventoryService;
    }

    @Test
    public void testThatCreateInventorySuccessfullyReturnsHttp201Created() throws Exception {
        InventoryEntity testInventoryA = TestDataUtil.createTestInventoryA();
        testInventoryA.setId(null);
        String inventoryJson = objectMapper.writeValueAsString(testInventoryA);
        mockMvc.perform(MockMvcRequestBuilders.post("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateInventorySuccessfullyReturnsSavedInventory() throws Exception {
        InventoryEntity testInventoryA = TestDataUtil.createTestInventoryA();
        testInventoryA.setId(null);
        String inventoryJson = objectMapper.writeValueAsString(testInventoryA);
        mockMvc.perform(MockMvcRequestBuilders.post("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(inventoryJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListInventoriesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListInventoriesReturnsListOfInventories() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        inventoryService.save(inventoryEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetInventoryReturnsHttpStatus200WhenInventoryExists() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        inventoryService.save(inventoryEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetInventoryReturnsHttpStatus404WhenNoInventoryExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetInventoryReturnsInventoryWhenInventoryExists() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        inventoryService.save(inventoryEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );;
    }

    @Test
    public void testThatFullUpdateInventoryReturnsHttpStatus404WhenNoInventoryExists() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        String inventoryEntityJson = objectMapper.writeValueAsString(inventoryEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventoryEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateInventoryReturnsHttpStatus200WhenInventoryExists() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        InventoryEntity savedInventoryEntity = inventoryService.save(inventoryEntityA);

        String inventoryEntityJson = objectMapper.writeValueAsString(inventoryEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/inventory/"+savedInventoryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventoryEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingInventory() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        InventoryEntity savedInventoryEntity = inventoryService.save(inventoryEntityA);

        InventoryEntity inventoryEntity = TestDataUtil.createTestInventoryB();
        inventoryEntity.setId(savedInventoryEntity.getId());
        String inventoryEntityUpdateJson = objectMapper.writeValueAsString(inventoryEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/inventory/"+savedInventoryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventoryEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedInventoryEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateInventoryReturnsHttpStatus200WhenInventoryExists() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        InventoryEntity savedInventoryEntity = inventoryService.save(inventoryEntityA);

        String inventoryEntityJson = objectMapper.writeValueAsString(inventoryEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/inventory/"+savedInventoryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventoryEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingInventoryReturnsUpdatedInventory() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        InventoryEntity savedInventoryEntity = inventoryService.save(inventoryEntityA);

        InventoryEntity inventoryEntity = TestDataUtil.createTestInventoryB();
        inventoryEntity.setId(savedInventoryEntity.getId());
        String inventoryEntityUpdateJson = objectMapper.writeValueAsString(inventoryEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/inventory/"+savedInventoryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inventoryEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedInventoryEntity.getId()));
    }

    @Test
    public void testThatDeleteInventoryReturnsHttpStatus204ForNonExistingInventory() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/inventory/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteInventoryReturnsHttpStatus204ForExistingInventory() throws Exception {
        InventoryEntity inventoryEntityA = TestDataUtil.createTestInventoryA();
        InventoryEntity savedInventoryEntity = inventoryService.save(inventoryEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/inventory/"+savedInventoryEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
