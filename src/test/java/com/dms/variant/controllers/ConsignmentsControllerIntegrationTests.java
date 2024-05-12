package com.dms.variant.controllers;

import com.dms.variant.domain.ConsignmentsEntity;
import com.dms.variant.domain.EmployeesEntity;
import com.dms.variant.services.ConsignmentsService;
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
public class ConsignmentsControllerIntegrationTests {

    private ConsignmentsService consignmentsService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public ConsignmentsControllerIntegrationTests(ConsignmentsService consignmentsService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.consignmentsService = consignmentsService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateConsignmentsSuccessfullyReturnsHttp201Created() throws Exception {
        ConsignmentsEntity testConsignmentsA = TestDataUtil.createTestConsignmentsA();
        testConsignmentsA.setId(null);
        String consignmentsJson = objectMapper.writeValueAsString(testConsignmentsA);
        mockMvc.perform(MockMvcRequestBuilders.post("/consignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(consignmentsJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateConsignmentsSuccessfullyReturnsSavedConsignments() throws Exception {
        ConsignmentsEntity testConsignmentsA = TestDataUtil.createTestConsignmentsA();
        testConsignmentsA.setId(null);
        String consignmentsJson = objectMapper.writeValueAsString(testConsignmentsA);
        mockMvc.perform(MockMvcRequestBuilders.post("/consignments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(consignmentsJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListConsignmentsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/consignments")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListConsignmentsReturnsListOfConsignments() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        consignmentsService.save(consignmentsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/consignments")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetConsignmentsReturnsHttpStatus200WhenConsignmentsExists() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        consignmentsService.save(consignmentsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/consignments/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetConsignmentsReturnsHttpStatus404WhenNoConsignmentsExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/consignments/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetConsignmentsReturnsConsignmentsWhenConsignmentsExists() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        consignmentsService.save(consignmentsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/consignments/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateConsignmentsReturnsHttpStatus404WhenNoConsignmentsExists() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        String consignmentsEntityJson = objectMapper.writeValueAsString(consignmentsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/consignments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(consignmentsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateConsignmentsReturnsHttpStatus200WhenConsignmentsExists() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        ConsignmentsEntity savedConsignmentsEntity = consignmentsService.save(consignmentsEntityA);

        String consignmentsEntityJson = objectMapper.writeValueAsString(savedConsignmentsEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/consignments/"+savedConsignmentsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(consignmentsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingConsignments() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        ConsignmentsEntity savedConsignmentsEntity = consignmentsService.save(consignmentsEntityA);

        ConsignmentsEntity consignmentsEntity = TestDataUtil.createTestConsignmentsA();
        consignmentsEntity.setId(savedConsignmentsEntity.getId());
        String consignmentsEntityUpdateJson = objectMapper.writeValueAsString(consignmentsEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/consignments/"+savedConsignmentsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(consignmentsEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedConsignmentsEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateConsignmentsReturnsHttpStatus200WhenConsignmentsExists() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        ConsignmentsEntity savedConsignmentsEntity = consignmentsService.save(consignmentsEntityA);

        String consignmentsEntityJson = objectMapper.writeValueAsString(consignmentsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/consignments/"+savedConsignmentsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(consignmentsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingConsignmentsReturnsUpdatedConsignments() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        ConsignmentsEntity savedConsignmentsEntity = consignmentsService.save(consignmentsEntityA);

        ConsignmentsEntity consignmentsEntity = TestDataUtil.createTestConsignmentsA();
        consignmentsEntity.setId(savedConsignmentsEntity.getId());
        String consignmentsEntityUpdateJson = objectMapper.writeValueAsString(consignmentsEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/consignments/"+savedConsignmentsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(consignmentsEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedConsignmentsEntity.getId()));
    }

    @Test
    public void testThatDeleteConsignmentsReturnsHttpStatus204ForNonExistingConsignments() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/consignments/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteConsignmentsReturnsHttpStatus204ForExistingConsignments() throws Exception {
        ConsignmentsEntity consignmentsEntityA = TestDataUtil.createTestConsignmentsA();
        ConsignmentsEntity savedConsignmentsEntity = consignmentsService.save(consignmentsEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/consignments/"+savedConsignmentsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
