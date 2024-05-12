package com.dms.variant.controllers;

import com.dms.variant.domain.AccountsEntity;
import com.dms.variant.domain.ConsignmentsEntity;
import com.dms.variant.services.AccountsService;
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
public class AccountsControllerIntegrationTests {

    private AccountsService accountsService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    public AccountsControllerIntegrationTests(AccountsService accountsService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.accountsService = accountsService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateAccountsSuccessfullyReturnsHttp201Created() throws Exception {
        AccountsEntity testAccountsA = TestDataUtil.createTestAccountsA();
        testAccountsA.setId(null);
        String accountsJson = objectMapper.writeValueAsString(testAccountsA);
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountsJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAccountsSuccessfullyReturnsSavedAccounts() throws Exception {
        AccountsEntity testAccountsA = TestDataUtil.createTestAccountsA();
        testAccountsA.setId(null);
        String accountsJson = objectMapper.writeValueAsString(testAccountsA);
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountsJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatListAccountsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListAccountsReturnsListOfAccounts() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        accountsService.save(accountsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("content[0].id").isNumber()
        );
    }

    @Test
    public void testThatGetAccountsReturnsHttpStatus200WhenAccountsExists() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        accountsService.save(accountsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetAccountsReturnsHttpStatus404WhenNoAccountsExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatGetAccountsReturnsAccountsWhenAccountsExists() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        accountsService.save(accountsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        );
    }

    @Test
    public void testThatFullUpdateAccountsReturnsHttpStatus404WhenNoAccountsExists() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        String accountsEntityJson = objectMapper.writeValueAsString(accountsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateAccountsReturnsHttpStatus200WhenAccountsExists() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        AccountsEntity savedAccountsEntity = accountsService.save(accountsEntityA);

        String accountsEntityJson = objectMapper.writeValueAsString(savedAccountsEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/accounts/"+savedAccountsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAccounts() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        AccountsEntity savedAccountsEntity = accountsService.save(accountsEntityA);

        AccountsEntity accountsEntity = TestDataUtil.createTestAccountsA();
        accountsEntity.setId(savedAccountsEntity.getId());
        String accountsEntityUpdateJson = objectMapper.writeValueAsString(accountsEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/accounts/"+savedAccountsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountsEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAccountsEntity.getId()));
    }

    @Test
    public void testThatPartialUpdateAccountsReturnsHttpStatus200WhenAccountsExists() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        AccountsEntity savedAccountsEntity = accountsService.save(accountsEntityA);

        String accountsEntityJson = objectMapper.writeValueAsString(accountsEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/accounts/"+savedAccountsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountsEntityJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateExistingAccountsReturnsUpdatedAccounts() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        AccountsEntity savedAccountsEntity = accountsService.save(accountsEntityA);

        AccountsEntity accountsEntity = TestDataUtil.createTestAccountsA();
        accountsEntity.setId(savedAccountsEntity.getId());
        String accountsEntityUpdateJson = objectMapper.writeValueAsString(accountsEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/accounts/"+savedAccountsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(accountsEntityUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAccountsEntity.getId()));
    }

    @Test
    public void testThatDeleteAccountsReturnsHttpStatus204ForNonExistingAccounts() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/accounts/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAccountsReturnsHttpStatus204ForExistingAccounts() throws Exception {
        AccountsEntity accountsEntityA = TestDataUtil.createTestAccountsA();
        AccountsEntity savedAccountsEntity = accountsService.save(accountsEntityA);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/accounts/"+savedAccountsEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
