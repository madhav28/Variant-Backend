package com.dms.variant.controllers;

import com.dms.variant.domain.InventoryEntity;
import com.dms.variant.domain.ServicesEntity;
import com.dms.variant.services.ServicesService;
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
public class ServicesControllerIntegrationTests {

    private ServicesService servicesService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public ServicesControllerIntegrationTests(ServicesService servicesService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.servicesService = servicesService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateServicesSuccessfullyReturnsHttp201Created() throws Exception {
        ServicesEntity testServicesA = TestDataUtil.createTestServicesA();
        testServicesA.setId(null);
        String servicesJson = objectMapper.writeValueAsString(testServicesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(servicesJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateServicesSuccessfullyReturnsSavedServices() throws Exception {
        ServicesEntity testServicesA = TestDataUtil.createTestServicesA();
        testServicesA.setId(null);
        String servicesJson = objectMapper.writeValueAsString(testServicesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(servicesJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListServicesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/services")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListServicesReturnsListOfServices() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        servicesService.save(servicesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/services")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetServicesReturnsHttpStatus200WhenServicesExists() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        servicesService.save(servicesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetServicesReturnsHttpStatus404WhenNoServicesExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetServicesReturnsServicesWhenServicesExists() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        servicesService.save(servicesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );;
    }

    @Test
    public void testThatFullUpdateServicesReturnsHttpStatus404WhenNoServicesExists() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        String servicesEntityJson = objectMapper.writeValueAsString(servicesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(servicesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateServicesReturnsHttpStatus200WhenServicesExists() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        ServicesEntity savedServicesEntity = servicesService.save(servicesEntityA);

        String servicesEntityJson = objectMapper.writeValueAsString(savedServicesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/services/"+savedServicesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(servicesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingServices() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        ServicesEntity savedServicesEntity = servicesService.save(servicesEntityA);

        ServicesEntity servicesEntity = TestDataUtil.createTestServicesB();
        servicesEntity.setId(savedServicesEntity.getId());
        String servicesEntityUpdateJson = objectMapper.writeValueAsString(servicesEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/services/"+savedServicesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(servicesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedServicesEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateServicesReturnsHttpStatus200WhenServicesExists() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        ServicesEntity savedServicesEntity = servicesService.save(servicesEntityA);

        String servicesEntityJson = objectMapper.writeValueAsString(servicesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/services/"+savedServicesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(servicesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingServicesReturnsUpdatedServices() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        ServicesEntity savedServicesEntity = servicesService.save(servicesEntityA);

        ServicesEntity servicesEntity = TestDataUtil.createTestServicesB();
        servicesEntity.setId(savedServicesEntity.getId());
        String servicesEntityUpdateJson = objectMapper.writeValueAsString(servicesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/services/"+savedServicesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(servicesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedServicesEntity.getId()));
    }

    @Test
    public void testThatDeleteServicesReturnsHttpStatus204ForNonExistingServices() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/services/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteServicesReturnsHttpStatus204ForExistingServices() throws Exception {
        ServicesEntity servicesEntityA = TestDataUtil.createTestServicesA();
        ServicesEntity savedServicesEntity = servicesService.save(servicesEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/services/"+savedServicesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
