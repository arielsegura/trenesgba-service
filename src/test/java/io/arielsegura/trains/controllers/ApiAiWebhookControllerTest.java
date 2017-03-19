package io.arielsegura.trains.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.arielsegura.trains.model.Result;
import io.arielsegura.trains.services.TrainService;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by arielsegura on 3/19/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiAiWebhookController.class, MockMvcAutoConfiguration.class},
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApiAiWebhookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrainService trainService;
    private String body;
    private Result mockResult;
    private String aiInput;

    @Before
    public void setUp() throws IOException {
        aiInput = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("stubs/aiInput.json"), "UTF-8");
        mockResult = new Result();
        mockResult.setFirstArrivalToOrigin("5");
        mockResult.setSecondArrivalToOrigin("21");
        given(trainService.resolve(anyString(), anyString()))
                .willReturn(mockResult);
    }

    @Test
    public void handle() throws Exception {
        String expected = new ObjectMapper().writeValueAsString(mockResult);
        mockMvc.perform(post("/webhook").content(aiInput).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(mvcResult -> JSONAssert.assertEquals(expected, mvcResult.getResponse().getContentAsString(), true));
    }

}