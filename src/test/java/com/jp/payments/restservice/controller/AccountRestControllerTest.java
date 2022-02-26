package com.jp.payments.restservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jp.payments.restservice.model.ApiRequest;
import com.jp.payments.restservice.model.ApiResponse;
import com.jp.payments.restservice.model.SourceResponse;
import com.jp.payments.restservice.service.SourceApiService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountRestController.class)
public class AccountRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SourceApiService sourceApiService;

    @Test
    public void validateAccount() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/validate-account")
                        .content(asJsonString(new ApiRequest("", new ArrayList<>())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void validateAccountWithSources() throws Exception {
        ApiRequest apiRequest = new ApiRequest("A123456", Arrays.asList("source1","source2"));
        ApiResponse apiResponse = new ApiResponse(Arrays.asList(new SourceResponse("source1", true), new SourceResponse("source2", false)));
        Mockito.when(sourceApiService.getResultFromSourceApi(apiRequest)).thenReturn(apiResponse);
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/validate-account")
                        .content(asJsonString(apiRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(apiResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}