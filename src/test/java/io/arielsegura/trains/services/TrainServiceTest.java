package io.arielsegura.trains.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.arielsegura.trains.model.Result;
import io.arielsegura.trains.services.external.ExternalAPIClient;
import io.arielsegura.trains.services.external.ExternalAPIResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by arielsegura on 3/19/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TrainService.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TrainServiceTest {

    @Autowired
    TrainService trainService;

    @MockBean
    ExternalAPIClient externalAPIClient;

    @Before
    public void setUp() throws IOException {
        // Stubbing WireMock
        String body = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("example.json"), "UTF-8");
        given(externalAPIClient.getData(anyInt()))
                .willReturn(new ObjectMapper().readValue(body, ExternalAPIResponse.class));
    }

    @Test
    public void resolve() throws Exception {
        Result lomas = trainService.resolve("Lomas", "");
        assertEquals("1", lomas.getFirstArrivalToOrigin());
        assertEquals("21", lomas.getSecondArrivalToOrigin());

        verify(externalAPIClient, times(1)).getData(15);
        verify(externalAPIClient, times(1)).getData(17);
        verify(externalAPIClient, times(1)).getData(19);
    }

}
