/*
 * Copyright © 2021 Kry. All Rights Reserved.
 */
package com.kry.codingtest.servicepoller;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.kry.codingtest.servicepoller.entity.Service;
import com.kry.codingtest.servicepoller.repository.ServicePollerRepository;
import com.kry.codingtest.servicepoller.util.Status;

@SpringBootApplication
@EnableScheduling
public class ServicePollerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePollerApplication.class, args);
	}

	@Bean
	protected CommandLineRunner init(final ServicePollerRepository servicePollerRepository) {

		return args -> {
			Service service = new Service();
			service.setName("Kry");
			service.setUrl("https://www.kry.se/");
			service.setCreatedAt(new Date());
			service.setStatus(Status.UNKNOWN);
			servicePollerRepository.save(service);
			service.setName("KryFail");
			service.setUrl("https://www.kri.se/");
			service.setCreatedAt(new Date());
			service.setStatus(Status.UNKNOWN);
			servicePollerRepository.save(service);
		};
	}

}
