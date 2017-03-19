package io.arielsegura.trains.services;

import io.arielsegura.trains.model.Result;
import io.arielsegura.trains.services.external.ExternalAPIClient;
import io.arielsegura.trains.services.external.ExternalAPIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by arielsegura on 3/18/17.
 */
@Service
public class TrainService {

    static Logger logger = LoggerFactory.getLogger(TrainService.class);

    @Autowired
    ExternalAPIClient externalAPIClient;

    public Result resolve(String from, String to) {
        logger.info("Resolving from %s to %s. ", from, to);
        Result result = new Result();
        //TODO use param to
        Stream<ExternalAPIResponse.Arrival> gutierrez = externalAPIClient.getData(15) // TODO externalize this prop
                .getResponse()
                .setMetadata("gutierrez")
                .getArrivals()
                .stream();
        Stream<ExternalAPIResponse.Arrival> korn = externalAPIClient.getData(17)// TODO externalize this prop
                .getResponse()
                .setMetadata("korn")
                .getArrivals()
                .stream();
        Stream<ExternalAPIResponse.Arrival> ezeiza = externalAPIClient.getData(19)// TODO externalize this prop
                .getResponse()
                .setMetadata("ezeiza")
                .getArrivals()
                .stream();
        Stream<ExternalAPIResponse.Arrival> allArrivals = Stream.concat(Stream.concat(gutierrez, korn), ezeiza);
        Optional<ExternalAPIResponse.Arrival> first = allArrivals
                .filter(arrival -> arrival.getNombre().toLowerCase().contains(from.toLowerCase()))
                .sorted((o1, o2) -> Integer.valueOf(o1.getMinutos_3()).compareTo(Integer.valueOf(o2.getMinutos_3())))
                .findFirst();
        if(first.isPresent()){
            ExternalAPIResponse.Arrival arrival = first.get();
            result.setLine(arrival.getLine());
            result.setFirstArrivalToOrigin(arrival.getMinutos_3());
            result.setSecondArrivalToOrigin(arrival.getMinutos_4());
        }

        return result;
    }


}
