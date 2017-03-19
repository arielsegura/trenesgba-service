package io.arielsegura.trains.services.external;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertFalse;

/**
 * Created by arielsegura on 3/18/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureWireMock(port = 9999)
public class ExternalAPIClientTest {

    ExternalAPIClient externalAPIClient;

    @Autowired
    Environment environment;

    @Before
    public void setUp() throws IOException {
        // Stubbing WireMock
        String body = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("example.json"), "UTF-8");
        stubFor(get(urlEqualTo("/api/v1/data/15"))
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(body)));
        externalAPIClient = ExternalAPIClient.connect("http://localhost:" + environment.getProperty("wiremock.server.port"));
    }

    @Test
    public void getData() throws Exception {
        ExternalAPIResponse data = externalAPIClient.getData(15);
        assertFalse(data.getResponse().getArrivals().isEmpty());
    }

}