package com.dms.variant.controllers;

import com.dms.variant.domain.SuppliersEntity;
import com.dms.variant.domain.VendorsEntity;
import com.dms.variant.services.VendorsService;
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
public class VendorsControllerIntegrationTests {

    private VendorsService vendorsService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public VendorsControllerIntegrationTests(VendorsService vendorsService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.vendorsService = vendorsService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateVendorsSuccessfullyReturnsHttp201Created() throws Exception {
        VendorsEntity testVendorsA = TestDataUtil.createTestVendorsA();
        testVendorsA.setId(null);
        String vendorsJson = objectMapper.writeValueAsString(testVendorsA);
        mockMvc.perform(MockMvcRequestBuilders.post("/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendorsJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateVendorsSuccessfullyReturnsSavedVendors() throws Exception {
        VendorsEntity testVendorsA = TestDataUtil.createTestVendorsA();
        testVendorsA.setId(null);
        String vendorsJson = objectMapper.writeValueAsString(testVendorsA);
        mockMvc.perform(MockMvcRequestBuilders.post("/vendors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(vendorsJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListVendorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vendors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListVendorsReturnsListOfVendors() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        vendorsService.save(vendorsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vendors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetVendorsReturnsHttpStatus200WhenVendorsExists() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        vendorsService.save(vendorsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetVendorsReturnsHttpStatus404WhenNoVendorsExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetVendorsReturnsVendorsWhenVendorsExists() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        vendorsService.save(vendorsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateVendorsReturnsHttpStatus404WhenNoVendorsExists() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        String vendorsEntityJson = objectMapper.writeValueAsString(vendorsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vendorsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateVendorsReturnsHttpStatus200WhenVendorsExists() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        VendorsEntity savedVendorsEntity = vendorsService.save(vendorsEntityA);

        String vendorsEntityJson = objectMapper.writeValueAsString(savedVendorsEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/vendors/"+savedVendorsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vendorsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingVendors() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        VendorsEntity savedVendorsEntity = vendorsService.save(vendorsEntityA);

        VendorsEntity vendorsEntity = TestDataUtil.createTestVendorsA();
        vendorsEntity.setId(savedVendorsEntity.getId());
        String vendorsEntityUpdateJson = objectMapper.writeValueAsString(vendorsEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/vendors/"+savedVendorsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vendorsEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedVendorsEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateVendorsReturnsHttpStatus200WhenVendorsExists() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        VendorsEntity savedVendorsEntity = vendorsService.save(vendorsEntityA);

        String vendorsEntityJson = objectMapper.writeValueAsString(vendorsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/vendors/"+savedVendorsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vendorsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingVendorsReturnsUpdatedVendors() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        VendorsEntity savedVendorsEntity = vendorsService.save(vendorsEntityA);

        VendorsEntity vendorsEntity = TestDataUtil.createTestVendorsA();
        vendorsEntity.setId(savedVendorsEntity.getId());
        String vendorsEntityUpdateJson = objectMapper.writeValueAsString(vendorsEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/vendors/"+savedVendorsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vendorsEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedVendorsEntity.getId()));
    }

    @Test
    public void testThatDeleteVendorsReturnsHttpStatus204ForNonExistingVendors() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/vendors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteVendorsReturnsHttpStatus204ForExistingVendors() throws Exception {
        VendorsEntity vendorsEntityA = TestDataUtil.createTestVendorsA();
        VendorsEntity savedVendorsEntity = vendorsService.save(vendorsEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/vendors/"+savedVendorsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
