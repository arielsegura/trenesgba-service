package io.arielsegura.trains.controllers;

import io.arielsegura.trains.model.Result;
import io.arielsegura.trains.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by arielsegura on 3/18/17.
 */
@RestController("/api/v1/trains")
public class TrainController {

    @Autowired
    TrainService trainService;

    @GetMapping
    public Result getNextArrivals(@RequestParam("from") String from, @RequestParam("to") String to){
        return trainService.resolve(from, to);
    }
}
