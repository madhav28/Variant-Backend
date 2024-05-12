package com.dms.variant.controllers;

import com.dms.variant.domain.AccountsEntity;
import com.dms.variant.domain.TaxCodesEntity;
import com.dms.variant.services.TaxCodesService;
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
public class TaxCodesControllerIntegrationTests {

    private TaxCodesService taxCodesService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public TaxCodesControllerIntegrationTests(TaxCodesService taxCodesService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.taxCodesService = taxCodesService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateTaxCodesSuccessfullyReturnsHttp201Created() throws Exception {
        TaxCodesEntity testTaxCodesA = TestDataUtil.createTestTaxCodesA();
        testTaxCodesA.setId(null);
        String taxCodesJson = objectMapper.writeValueAsString(testTaxCodesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/taxcodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taxCodesJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTaxCodesSuccessfullyReturnsSavedTaxCodes() throws Exception {
        TaxCodesEntity testTaxCodesA = TestDataUtil.createTestTaxCodesA();
        testTaxCodesA.setId(null);
        String taxCodesJson = objectMapper.writeValueAsString(testTaxCodesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/taxcodes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taxCodesJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListTaxCodesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxcodes")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListTaxCodesReturnsListOfTaxCodes() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        taxCodesService.save(taxCodesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxcodes")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetTaxCodesReturnsHttpStatus200WhenTaxCodesExists() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        taxCodesService.save(taxCodesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxcodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetTaxCodesReturnsHttpStatus404WhenNoTaxCodesExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxcodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetTaxCodesReturnsTaxCodesWhenTaxCodesExists() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        taxCodesService.save(taxCodesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/taxcodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateTaxCodesReturnsHttpStatus404WhenNoTaxCodesExists() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        String taxCodesEntityJson = objectMapper.writeValueAsString(taxCodesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/taxcodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxCodesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateTaxCodesReturnsHttpStatus200WhenTaxCodesExists() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        TaxCodesEntity savedTaxCodesEntity = taxCodesService.save(taxCodesEntityA);

        String taxCodesEntityJson = objectMapper.writeValueAsString(savedTaxCodesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/taxcodes/"+savedTaxCodesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxCodesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingTaxCodes() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        TaxCodesEntity savedTaxCodesEntity = taxCodesService.save(taxCodesEntityA);

        TaxCodesEntity taxCodesEntity = TestDataUtil.createTestTaxCodesA();
        taxCodesEntity.setId(savedTaxCodesEntity.getId());
        String taxCodesEntityUpdateJson = objectMapper.writeValueAsString(taxCodesEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/taxcodes/"+savedTaxCodesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxCodesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTaxCodesEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateTaxCodesReturnsHttpStatus200WhenTaxCodesExists() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        TaxCodesEntity savedTaxCodesEntity = taxCodesService.save(taxCodesEntityA);

        String taxCodesEntityJson = objectMapper.writeValueAsString(taxCodesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/taxcodes/"+savedTaxCodesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxCodesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingTaxCodesReturnsUpdatedTaxCodes() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        TaxCodesEntity savedTaxCodesEntity = taxCodesService.save(taxCodesEntityA);

        TaxCodesEntity taxCodesEntity = TestDataUtil.createTestTaxCodesA();
        taxCodesEntity.setId(savedTaxCodesEntity.getId());
        String taxCodesEntityUpdateJson = objectMapper.writeValueAsString(taxCodesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/taxcodes/"+savedTaxCodesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taxCodesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTaxCodesEntity.getId()));
    }

    @Test
    public void testThatDeleteTaxCodesReturnsHttpStatus204ForNonExistingTaxCodes() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/taxcodes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteTaxCodesReturnsHttpStatus204ForExistingTaxCodes() throws Exception {
        TaxCodesEntity taxCodesEntityA = TestDataUtil.createTestTaxCodesA();
        TaxCodesEntity savedTaxCodesEntity = taxCodesService.save(taxCodesEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/taxcodes/"+savedTaxCodesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
