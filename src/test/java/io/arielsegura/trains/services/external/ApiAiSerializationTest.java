package io.arielsegura.trains.services.external;

import ai.api.GsonFactory;
import ai.api.model.AIResponse;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

/**
 * Created by arielsegura on 3/19/17.
 */
public class ApiAiSerializationTest {

    private String aiInput;

    private final static Gson GSON = GsonFactory.getDefaultFactory().getGson();

    @Test
    public void simpleTest() throws Exception {
        aiInput = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("stubs/aiInput.json"), "UTF-8");
        AIResponse aiResponse = GSON.fromJson(aiInput, AIResponse.class);
        System.out.println(aiResponse);
    }
}
