package com.dms.variant.controllers;

import com.dms.variant.domain.TaxAuthoritiesEntity;
import com.dms.variant.domain.TaxCodesEntity;
import com.dms.variant.services.TaxAuthoritiesService;
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
public class TaxAuthoritiesControllerIntegrationTests {

    private TaxAuthoritiesService taxAuthoritiesService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public TaxAuthoritiesControllerIntegrationTests(TaxAuthoritiesService taxAuthoritiesService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.taxAuthoritiesService = taxAuthoritiesService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateTaxAuthoritiesSuccessfullyReturnsHttp201Created() throws Exception {
        TaxAuthoritiesEntity testTaxAuthoritiesA = TestDataUtil.createTestTaxAuthoritiesA();
        testTaxAuthoritiesA.setId(null);
        String taxAuthoritiesJson = objectMapper.writeValueAsString(testTaxAuthoritiesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/taxauthorities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taxAuthoritiesJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTaxAuthoritiesSuccessfullyReturnsSavedTaxAuthorities() throws Exception {
        TaxAuthoritiesEntity testTaxAuthoritiesA = TestDataUtil.createTestTaxAuthoritiesA();
        testTaxAuthoritiesA.setId(null);
        String taxAuthoritiesJson = objectMapper.writeValueAsString(testTaxAuthoritiesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/taxauthorities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taxAuthoritiesJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListTaxAuthoritiesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxauthorities")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListTaxAuthoritiesReturnsListOfTaxAuthorities() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        taxAuthoritiesService.save(taxAuthoritiesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxauthorities")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetTaxAuthoritiesReturnsHttpStatus200WhenTaxAuthoritiesExists() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        taxAuthoritiesService.save(taxAuthoritiesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxauthorities/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetTaxAuthoritiesReturnsHttpStatus404WhenNoTaxAuthoritiesExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxauthorities/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetTaxAuthoritiesReturnsTaxAuthoritiesWhenTaxAuthoritiesExists() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        taxAuthoritiesService.save(taxAuthoritiesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxauthorities/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateTaxAuthoritiesReturnsHttpStatus404WhenNoTaxAuthoritiesExists() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        String taxAuthoritiesEntityJson = objectMapper.writeValueAsString(taxAuthoritiesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/taxauthorities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxAuthoritiesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateTaxAuthoritiesReturnsHttpStatus200WhenTaxAuthoritiesExists() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        TaxAuthoritiesEntity savedTaxAuthoritiesEntity = taxAuthoritiesService.save(taxAuthoritiesEntityA);

        String taxAuthoritiesEntityJson = objectMapper.writeValueAsString(savedTaxAuthoritiesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/taxauthorities/"+savedTaxAuthoritiesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxAuthoritiesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingTaxAuthorities() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        TaxAuthoritiesEntity savedTaxAuthoritiesEntity = taxAuthoritiesService.save(taxAuthoritiesEntityA);

        TaxAuthoritiesEntity taxAuthoritiesEntity = TestDataUtil.createTestTaxAuthoritiesA();
        taxAuthoritiesEntity.setId(savedTaxAuthoritiesEntity.getId());
        String taxAuthoritiesEntityUpdateJson = objectMapper.writeValueAsString(taxAuthoritiesEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/taxauthorities/"+savedTaxAuthoritiesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxAuthoritiesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTaxAuthoritiesEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateTaxAuthoritiesReturnsHttpStatus200WhenTaxAuthoritiesExists() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        TaxAuthoritiesEntity savedTaxAuthoritiesEntity = taxAuthoritiesService.save(taxAuthoritiesEntityA);

        String taxCodesEntityJson = objectMapper.writeValueAsString(taxAuthoritiesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/taxauthorities/"+savedTaxAuthoritiesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxCodesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingTaxAuthoritiesReturnsUpdatedTaxAuthorities() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        TaxAuthoritiesEntity savedTaxAuthoritiesEntity = taxAuthoritiesService.save(taxAuthoritiesEntityA);

        TaxAuthoritiesEntity taxAuthoritiesEntity = TestDataUtil.createTestTaxAuthoritiesA();
        taxAuthoritiesEntity.setId(savedTaxAuthoritiesEntity.getId());
        String taxAuthoritiesEntityUpdateJson = objectMapper.writeValueAsString(taxAuthoritiesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/taxauthorities/"+savedTaxAuthoritiesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxAuthoritiesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTaxAuthoritiesEntity.getId()));
    }

    @Test
    public void testThatDeleteTaxAuthoritiesReturnsHttpStatus204ForNonExistingTaxAuthorities() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/taxauthorities/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteTaxAuthoritiesReturnsHttpStatus204ForExistingTaxAuthorities() throws Exception {
        TaxAuthoritiesEntity taxAuthoritiesEntityA = TestDataUtil.createTestTaxAuthoritiesA();
        TaxAuthoritiesEntity savedTaxAuthoritiesEntity = taxAuthoritiesService.save(taxAuthoritiesEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/taxauthorities/"+savedTaxAuthoritiesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
