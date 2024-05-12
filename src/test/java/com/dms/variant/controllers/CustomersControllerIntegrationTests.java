package com.dms.variant.controllers;

import com.dms.variant.domain.CustomersEntity;
import com.dms.variant.domain.PackagesEntity;
import com.dms.variant.services.CustomersService;
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
public class CustomersControllerIntegrationTests {

    private CustomersService customersService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public CustomersControllerIntegrationTests(CustomersService customersService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.customersService = customersService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateCustomersSuccessfullyReturnsHttp201Created() throws Exception {
        CustomersEntity testCustomersA = TestDataUtil.createTestCustomersA();
        testCustomersA.setId(null);
        String customersJson = objectMapper.writeValueAsString(testCustomersA);
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customersJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateCustomersSuccessfullyReturnsSavedCustomers() throws Exception {
        CustomersEntity testCustomersA = TestDataUtil.createTestCustomersA();
        testCustomersA.setId(null);
        String customersJson = objectMapper.writeValueAsString(testCustomersA);
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customersJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListCustomersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListCustomersReturnsListOfCustomers() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        customersService.save(customersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetCustomersReturnsHttpStatus200WhenCustomersExists() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        customersService.save(customersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetCustomersReturnsHttpStatus404WhenNoCustomersExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetCustomersReturnsCustomersWhenCustomersExists() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        customersService.save(customersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateCustomersReturnsHttpStatus404WhenNoCustomersExists() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        String customersEntityJson = objectMapper.writeValueAsString(customersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customersEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateCustomersReturnsHttpStatus200WhenCustomersExists() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        CustomersEntity savedCustomersEntity = customersService.save(customersEntityA);

        String customersEntityJson = objectMapper.writeValueAsString(savedCustomersEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/customers/"+savedCustomersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customersEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingCustomers() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        CustomersEntity savedCustomersEntity = customersService.save(customersEntityA);

        CustomersEntity customersEntity = TestDataUtil.createTestCustomersA();
        customersEntity.setId(savedCustomersEntity.getId());
        String customersEntityUpdateJson = objectMapper.writeValueAsString(customersEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/customers/"+savedCustomersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customersEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCustomersEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateCustomersReturnsHttpStatus200WhenCustomersExists() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        CustomersEntity savedCustomersEntity = customersService.save(customersEntityA);

        String customersEntityJson = objectMapper.writeValueAsString(customersEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/customers/"+savedCustomersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customersEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingCustomersReturnsUpdatedCustomers() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        CustomersEntity savedCustomersEntity = customersService.save(customersEntityA);

        CustomersEntity customersEntity = TestDataUtil.createTestCustomersA();
        customersEntity.setId(savedCustomersEntity.getId());
        String customersEntityUpdateJson = objectMapper.writeValueAsString(customersEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/customers/"+savedCustomersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customersEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedCustomersEntity.getId()));
    }

    @Test
    public void testThatDeleteCustomersReturnsHttpStatus204ForNonExistingCustomers() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteCustomersReturnsHttpStatus204ForExistingCustomers() throws Exception {
        CustomersEntity customersEntityA = TestDataUtil.createTestCustomersA();
        CustomersEntity savedCustomersEntity = customersService.save(customersEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/customers/"+savedCustomersEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
