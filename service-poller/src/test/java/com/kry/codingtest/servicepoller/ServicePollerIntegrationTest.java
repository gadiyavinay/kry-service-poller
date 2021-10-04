/*
 * Copyright © 2021 Kry. All Rights Reserved.
 */
package com.kry.codingtest.servicepoller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.kry.codingtest.servicepoller.entity.Service;
import com.kry.codingtest.servicepoller.repository.ServicePollerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServicePollerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServicePollerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ServicePollerRepository repo;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testListServices() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<?> response = restTemplate.exchange(getRootUrl() + "/api/services", HttpMethod.GET, entity,
				String.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testSaveService() {
		Service service = new Service();
		service.setName("Kry");
		service.setUrl("https://www.kry.se");
		ResponseEntity<?> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/add", service, Service.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
		restTemplate.delete(getRootUrl() + "/api/delete?name=" + "Kry");
	}

	@Test
	public void testDeleteService() {
		String name = "Kry";
		Optional<Service> service = repo.findByName(name);
		assertTrue(service.isPresent());
		restTemplate.delete(getRootUrl() + "/api/delete?name=" + name);
		service = repo.findByName(name);
		assertFalse(service.isPresent());

	}
}
