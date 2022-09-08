package com.contactanimeshs.twinimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Animesh (@contactanimeshs)
 */

@SpringBootApplication
@EnableFeignClients
public class TwinimalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwinimalApplication.class, args);
	}


}
