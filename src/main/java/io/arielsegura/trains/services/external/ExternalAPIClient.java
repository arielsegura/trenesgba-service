package io.arielsegura.trains.services.external;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.Logger;
import feign.Param;
import feign.RequestLine;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by arielsegura on 3/18/17.
 */
@FeignClient(name = "external-service", url = "${external-service.url}")
public interface ExternalAPIClient {

    @RequestLine("GET /api/v1/data/{variable}")
    ExternalAPIResponse getData(@Param("variable") int id);

    static ObjectMapper mapper(){
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    static ExternalAPIClient connect(String host){
        ObjectMapper mapper = mapper();

        return HystrixFeign.builder()
                .encoder(new JacksonEncoder(mapper))
                .decoder(new JacksonDecoder(mapper))
                .logLevel(Logger.Level.BASIC)
                .logger(new Slf4jLogger(ExternalAPIClient.class))
                .target(ExternalAPIClient.class, host, new ExternalAPIClientFallback());
    }

    class ExternalAPIClientFallback implements  ExternalAPIClient{

        @Override
        public ExternalAPIResponse getData(int id) {
            return new ExternalAPIResponse();
        }
    }
}
