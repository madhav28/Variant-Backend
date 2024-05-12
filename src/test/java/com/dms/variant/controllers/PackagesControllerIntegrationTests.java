package com.dms.variant.controllers;

import com.dms.variant.domain.PackagesEntity;
import com.dms.variant.domain.ServicesEntity;
import com.dms.variant.services.PackagesService;
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
public class PackagesControllerIntegrationTests {

    private PackagesService packagesService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public PackagesControllerIntegrationTests(PackagesService packagesService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.packagesService = packagesService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreatePackagesSuccessfullyReturnsHttp201Created() throws Exception {
        PackagesEntity testPackagesA = TestDataUtil.createTestPackagesA();
        testPackagesA.setId(null);
        String packagesJson = objectMapper.writeValueAsString(testPackagesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/packages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(packagesJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreatePackagesSuccessfullyReturnsSavedPackages() throws Exception {
        PackagesEntity testPackagesA = TestDataUtil.createTestPackagesA();
        testPackagesA.setId(null);
        String packagesJson = objectMapper.writeValueAsString(testPackagesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/packages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(packagesJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListPackagesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/packages")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListPackagesReturnsListOfPackages() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        packagesService.save(packagesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/packages")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetPackagesReturnsHttpStatus200WhenPackagesExists() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        packagesService.save(packagesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/packages/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetPackagesReturnsHttpStatus404WhenNoPackagesExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/packages/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetPackagesReturnsPackagesWhenPackagesExists() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        packagesService.save(packagesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/packages/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdatePackagesReturnsHttpStatus404WhenNoPackagesExists() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        String packagesEntityJson = objectMapper.writeValueAsString(packagesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/packages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packagesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdatePackagesReturnsHttpStatus200WhenPackagesExists() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        PackagesEntity savedPackagesEntity = packagesService.save(packagesEntityA);

        String packagesEntityJson = objectMapper.writeValueAsString(savedPackagesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/packages/"+savedPackagesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packagesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingPackages() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        PackagesEntity savedPackagesEntity = packagesService.save(packagesEntityA);

        PackagesEntity packagesEntity = TestDataUtil.createTestPackagesA();
        packagesEntity.setId(savedPackagesEntity.getId());
        String packagesEntityUpdateJson = objectMapper.writeValueAsString(packagesEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/packages/"+savedPackagesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packagesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPackagesEntity.getId()));
    }

    @Test
    public void testThatPartialUpdatePackagesReturnsHttpStatus200WhenPackagesExists() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        PackagesEntity savedPackagesEntity = packagesService.save(packagesEntityA);

        String packagesEntityJson = objectMapper.writeValueAsString(packagesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/packages/"+savedPackagesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packagesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingPackagesReturnsUpdatedPackages() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        PackagesEntity savedPackagesEntity = packagesService.save(packagesEntityA);

        PackagesEntity packagesEntity = TestDataUtil.createTestPackagesB();
        packagesEntity.setId(savedPackagesEntity.getId());
        String packagesEntityUpdateJson = objectMapper.writeValueAsString(packagesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/packages/"+savedPackagesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(packagesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPackagesEntity.getId()));
    }

    @Test
    public void testThatDeletePackagesReturnsHttpStatus204ForNonExistingPackages() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/packages/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeletePackagesReturnsHttpStatus204ForExistingPackages() throws Exception {
        PackagesEntity packagesEntityA = TestDataUtil.createTestPackagesA();
        PackagesEntity savedPackagesEntity = packagesService.save(packagesEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/packages/"+savedPackagesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
