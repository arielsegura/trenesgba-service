package io.arielsegura.trains.controllers;

import ai.api.GsonFactory;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Fulfillment;
import ai.api.model.ResponseMessage;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.arielsegura.trains.model.Result;
import io.arielsegura.trains.services.TrainService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by arielsegura on 3/19/17.
 */
@RestController("/webhook")
public class ApiAiWebhookController {

    static Logger logger = LoggerFactory.getLogger(ApiAiWebhookController.class);

    public final static String FROM_PARAMETER = "from";
    public final static String TO_PARAMETER = "to";

    @Value("${web-hook.defaultmessage.template}")
    String responseTemplate;

    @Autowired
    TrainService trainService;

    private final static Gson GSON = GsonFactory.getDefaultFactory().getGson();

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public String handle(@RequestBody String input){
        AIResponse aiResponse = GSON.fromJson(input, AIResponse.class);

        ai.api.model.Result aiResult = aiResponse.getResult();
        String fromParameter = aiResult.getStringParameter(FROM_PARAMETER);
        String toParameter = aiResult.getStringParameter(TO_PARAMETER);

        Assert.state(StringUtils.isNotEmpty(fromParameter), "Empty parameter 'from'. ");
        Assert.state(StringUtils.isNotEmpty(toParameter), "Empty parameter 'to'. ");

        logger.info("Webhook from {} to {}", fromParameter, toParameter);

        Result result = trainService.resolve(fromParameter, toParameter);

        Fulfillment fulfillment = aiResult.getFulfillment();

        String text = format(responseTemplate, result.getLine(), result.getFirstArrivalToOrigin(), result.getSecondArrivalToOrigin());

        fulfillment.setDisplayText(text);

        fulfillment.setSpeech(text);

        ResponseMessage.ResponsePayload responsePayload = new ResponseMessage.ResponsePayload();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", text);
        responsePayload.setPayload(jsonObject);

        ResponseMessage.ResponseSpeech responseSpeech = new ResponseMessage.ResponseSpeech();
        responseSpeech.setSpeech(text);

        fulfillment.setMessages(ImmutableList.of(responseSpeech));

        return GSON.toJson(fulfillment);
    }
}
