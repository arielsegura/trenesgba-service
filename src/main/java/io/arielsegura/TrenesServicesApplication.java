package io.arielsegura;

import io.arielsegura.trains.services.external.ExternalAPIClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableFeignClients
public class TrenesServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrenesServicesApplication.class, args);
	}

	@Bean
	public ExternalAPIClient externalAPIClient(@Value("${external-service.url}") String url){
		return ExternalAPIClient.connect(url);
	}

}
