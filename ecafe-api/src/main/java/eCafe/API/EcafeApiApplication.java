package eCafe.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcafeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcafeApiApplication.class, args);
	}

}
