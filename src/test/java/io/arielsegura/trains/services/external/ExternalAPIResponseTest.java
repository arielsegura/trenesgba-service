package io.arielsegura.trains.services.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertFalse;

/**
 * Created by arielsegura on 3/18/17.
 */
public class ExternalAPIResponseTest {

    @Test
    public void deserialize() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("example.json");
        ObjectMapper mapper = ExternalAPIClient.mapper();
        ExternalAPIResponse externalAPIResponse = mapper.readValue(inputStream, ExternalAPIResponse.class);
        assertFalse(externalAPIResponse.getResponse().getArrivals().isEmpty());
    }

}