package com.dms.variant.controllers;

import com.dms.variant.domain.AssociatesEntity;
import com.dms.variant.domain.VendorsEntity;
import com.dms.variant.services.AssociatesService;
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
public class AssociatesControllerIntegrationTests {

    private AssociatesService associatesService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AssociatesControllerIntegrationTests(AssociatesService associatesService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.associatesService = associatesService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateAssociatesSuccessfullyReturnsHttp201Created() throws Exception {
        AssociatesEntity testAssociatesA = TestDataUtil.createTestAssociatesA();
        testAssociatesA.setId(null);
        String associatesJson = objectMapper.writeValueAsString(testAssociatesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/associates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(associatesJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAssociatesSuccessfullyReturnsSavedAssociates() throws Exception {
        AssociatesEntity testAssociatesA = TestDataUtil.createTestAssociatesA();
        testAssociatesA.setId(null);
        String associatesJson = objectMapper.writeValueAsString(testAssociatesA);
        mockMvc.perform(MockMvcRequestBuilders.post("/associates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(associatesJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListAssociatesReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/associates")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAssociatesReturnsListOfAssociates() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        associatesService.save(associatesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/associates")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetAssociatesReturnsHttpStatus200WhenAssociatesExists() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        associatesService.save(associatesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/associates/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAssociatesReturnsHttpStatus404WhenNoAssociatesExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/associates/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetAssociatesReturnsAssociatesWhenAssociatesExists() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        associatesService.save(associatesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/associates/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateAssociatesReturnsHttpStatus404WhenNoAssociatesExists() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        String associatesEntityJson = objectMapper.writeValueAsString(associatesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/associates/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(associatesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAssociatesReturnsHttpStatus200WhenAssociatesExists() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        AssociatesEntity savedAssociatesEntity = associatesService.save(associatesEntityA);

        String associatesEntityJson = objectMapper.writeValueAsString(savedAssociatesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/associates/"+savedAssociatesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(associatesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAssociates() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        AssociatesEntity savedAssociatesEntity = associatesService.save(associatesEntityA);

        AssociatesEntity associatesEntity = TestDataUtil.createTestAssociatesA();
        associatesEntity.setId(savedAssociatesEntity.getId());
        String associatesEntityUpdateJson = objectMapper.writeValueAsString(associatesEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/associates/"+savedAssociatesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(associatesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAssociatesEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateAssociatesReturnsHttpStatus200WhenAssociatesExists() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        AssociatesEntity savedAssociatesEntity = associatesService.save(associatesEntityA);

        String associatesEntityJson = objectMapper.writeValueAsString(associatesEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/associates/"+savedAssociatesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(associatesEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingAssociatesReturnsUpdatedAssociates() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        AssociatesEntity savedAssociatesEntity = associatesService.save(associatesEntityA);

        AssociatesEntity associatesEntity = TestDataUtil.createTestAssociatesA();
        associatesEntity.setId(savedAssociatesEntity.getId());
        String associatesEntityUpdateJson = objectMapper.writeValueAsString(associatesEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/associates/"+savedAssociatesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(associatesEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAssociatesEntity.getId()));
    }

    @Test
    public void testThatDeleteAssociatesReturnsHttpStatus204ForNonExistingAssociates() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/associates/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAssociatesReturnsHttpStatus204ForExistingAssociates() throws Exception {
        AssociatesEntity associatesEntityA = TestDataUtil.createTestAssociatesA();
        AssociatesEntity savedAssociatesEntity = associatesService.save(associatesEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/associates/"+savedAssociatesEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
