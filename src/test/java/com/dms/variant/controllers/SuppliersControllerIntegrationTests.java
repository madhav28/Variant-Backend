package com.dms.variant.controllers;

import com.dms.variant.domain.CustomersEntity;
import com.dms.variant.domain.SuppliersEntity;
import com.dms.variant.services.SuppliersService;
import com.dms.variant.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class SuppliersControllerIntegrationTests {

    private SuppliersService suppliersService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public SuppliersControllerIntegrationTests(SuppliersService suppliersService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.suppliersService = suppliersService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateSuppliersSuccessfullyReturnsHttp201Created() throws Exception {
        SuppliersEntity testSuppliersA = TestDataUtil.createTestSuppliersA();
        testSuppliersA.setId(null);
        String suppliersJson = objectMapper.writeValueAsString(testSuppliersA);
        mockMvc.perform(MockMvcRequestBuilders.post("/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(suppliersJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateSuppliersSuccessfullyReturnsSavedSuppliers() throws Exception {
        SuppliersEntity testSuppliersA = TestDataUtil.createTestSuppliersA();
        testSuppliersA.setId(null);
        String suppliersJson = objectMapper.writeValueAsString(testSuppliersA);
        mockMvc.perform(MockMvcRequestBuilders.post("/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(suppliersJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListSuppliersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListSuppliersReturnsListOfSuppliers() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        suppliersService.save(suppliersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetSuppliersReturnsHttpStatus200WhenSuppliersExists() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        suppliersService.save(suppliersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetSuppliersReturnsHttpStatus404WhenNoSuppliersExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetSuppliersReturnsSuppliersWhenSuppliersExists() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        suppliersService.save(suppliersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateSuppliersReturnsHttpStatus404WhenNoSuppliersExists() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        String suppliersEntityJson = objectMapper.writeValueAsString(suppliersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suppliersEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateSuppliersReturnsHttpStatus200WhenSuppliersExists() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        SuppliersEntity savedSuppliersEntity = suppliersService.save(suppliersEntityA);

        String suppliersEntityJson = objectMapper.writeValueAsString(savedSuppliersEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/suppliers/"+savedSuppliersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suppliersEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingSuppliers() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        SuppliersEntity savedSuppliersEntity = suppliersService.save(suppliersEntityA);

        SuppliersEntity suppliersEntity = TestDataUtil.createTestSuppliersA();
        suppliersEntity.setId(savedSuppliersEntity.getId());
        String suppliersEntityUpdateJson = objectMapper.writeValueAsString(suppliersEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/suppliers/"+savedSuppliersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suppliersEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedSuppliersEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateSuppliersReturnsHttpStatus200WhenSuppliersExists() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        SuppliersEntity savedSuppliersEntity = suppliersService.save(suppliersEntityA);

        String suppliersEntityJson = objectMapper.writeValueAsString(suppliersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/suppliers/"+savedSuppliersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suppliersEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingSuppliersReturnsUpdatedSuppliers() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        SuppliersEntity savedSuppliersEntity = suppliersService.save(suppliersEntityA);

        SuppliersEntity suppliersEntity = TestDataUtil.createTestSuppliersA();
        suppliersEntity.setId(savedSuppliersEntity.getId());
        String suppliersEntityUpdateJson = objectMapper.writeValueAsString(suppliersEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/suppliers/"+savedSuppliersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(suppliersEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedSuppliersEntity.getId()));
    }

    @Test
    public void testThatDeleteSuppliersReturnsHttpStatus204ForNonExistingSuppliers() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/suppliers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteSuppliersReturnsHttpStatus204ForExistingSuppliers() throws Exception {
        SuppliersEntity suppliersEntityA = TestDataUtil.createTestSuppliersA();
        SuppliersEntity savedSuppliersEntity = suppliersService.save(suppliersEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/suppliers/"+savedSuppliersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
