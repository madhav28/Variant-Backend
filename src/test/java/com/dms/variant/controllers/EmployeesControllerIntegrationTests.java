package com.dms.variant.controllers;

import com.dms.variant.domain.AssociatesEntity;
import com.dms.variant.domain.EmployeesEntity;
import com.dms.variant.services.EmployessService;
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
public class EmployeesControllerIntegrationTests {

    private EmployessService employessService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public EmployeesControllerIntegrationTests(EmployessService employessService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.employessService = employessService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateEmployeesSuccessfullyReturnsHttp201Created() throws Exception {
        EmployeesEntity testEmployeesA = TestDataUtil.createTestEmployeesA();
        testEmployeesA.setId(null);
        String employeesJson = objectMapper.writeValueAsString(testEmployeesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeesJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateEmployeesSuccessfullyReturnsSavedEmployees() throws Exception {
        EmployeesEntity testEmployeesA = TestDataUtil.createTestEmployeesA();
        testEmployeesA.setId(null);
        String employeesJson = objectMapper.writeValueAsString(testEmployeesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeesJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListEmployeesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListEmployeesReturnsListOfEmployees() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        employessService.save(employeesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetEmployeesReturnsHttpStatus200WhenEmployeesExists() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        employessService.save(employeesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetEmployeesReturnsHttpStatus404WhenNoEmployeesExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetEmployeesReturnsEmployeesWhenEmployeesExists() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        employessService.save(employeesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateEmployeesReturnsHttpStatus404WhenNoEmployeesExists() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        String employeesEntityJson = objectMapper.writeValueAsString(employeesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateEmployeesReturnsHttpStatus200WhenEmployeesExists() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        EmployeesEntity savedEmployeesEntity = employessService.save(employeesEntityA);

        String employeesEntityJson = objectMapper.writeValueAsString(savedEmployeesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/employees/"+savedEmployeesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingEmployees() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        EmployeesEntity savedEmployeesEntity = employessService.save(employeesEntityA);

        EmployeesEntity employeesEntity = TestDataUtil.createTestEmployeesA();
        employeesEntity.setId(savedEmployeesEntity.getId());
        String employeesEntityUpdateJson = objectMapper.writeValueAsString(employeesEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/employees/"+savedEmployeesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedEmployeesEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateEmployeesReturnsHttpStatus200WhenEmployeesExists() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        EmployeesEntity savedEmployeesEntity = employessService.save(employeesEntityA);

        String employeesEntityJson = objectMapper.writeValueAsString(employeesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/employees/"+savedEmployeesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingEmployeesReturnsUpdatedEmployees() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        EmployeesEntity savedEmployeesEntity = employessService.save(employeesEntityA);

        EmployeesEntity employeesEntity = TestDataUtil.createTestEmployeesA();
        employeesEntity.setId(savedEmployeesEntity.getId());
        String employeesEntityUpdateJson = objectMapper.writeValueAsString(employeesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/employees/"+savedEmployeesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedEmployeesEntity.getId()));
    }

    @Test
    public void testThatDeleteEmployeesReturnsHttpStatus204ForNonExistingEmployees() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteEmployeesReturnsHttpStatus204ForExistingEmployees() throws Exception {
        EmployeesEntity employeesEntityA = TestDataUtil.createTestEmployeesA();
        EmployeesEntity savedEmployeesEntity = employessService.save(employeesEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/employees/"+savedEmployeesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
