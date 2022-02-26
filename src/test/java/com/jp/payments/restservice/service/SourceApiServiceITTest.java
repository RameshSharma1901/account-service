package com.jp.payments.restservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.jp.payments.restservice.model.ApiRequest;
import com.jp.payments.restservice.model.ApiResponse;
import com.jp.payments.restservice.model.SourceResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SourceApiServiceITTest {
    @Autowired
    private SourceApiService sourceApiService;
    @Autowired
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;
    private ObjectMapper mapper;
    Gson gson = new Gson();

    /* Request to API in JSON format */
    String apiRequestInJson = "{\"accountNumber\":\"A123456\",\"sources\":[\"source1\",\"source2\"]}";

    /* Mocked response from source1 */
    String mockedSource1ResponseInJson = "{\"source\":\"source1\",\"isValid\":true}";

    /* Mocked response from source2 */
    String mockedSource2ResponseInJson = "{\"source\":\"source2\",\"isValid\":false}";

    /* Expected API Response in JSON format */
    String expectedApiResponseInJson = "{\"result\":[{\"source\":\"source1\",\"isValid\":true},{\"source\":\"source2\",\"isValid\":false}]}";


    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mapper = new ObjectMapper();
    }

    @Test
    public void getResultFromSourceApi_givenSourceApiIsMocked() throws URISyntaxException, JsonProcessingException {

        mockServer.expect(ExpectedCount.once(),
                        MockRestRequestMatchers.requestTo(
                                new URI("https://source1.com/v1/api/account/validate")))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(gson.fromJson(mockedSource1ResponseInJson, SourceResponse.class)))
                );
        mockServer.expect(ExpectedCount.once(),
                        MockRestRequestMatchers.requestTo(
                                new URI("https://source2.com/v1/api/account/validate")))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(gson.fromJson(mockedSource2ResponseInJson, SourceResponse.class)))
                );
        ApiResponse response = sourceApiService.getResultFromSourceApi(gson.fromJson(apiRequestInJson, ApiRequest.class));

        mockServer.verify();

        Assertions.assertEquals(expectedApiResponseInJson, gson.toJson(response));

    }
}