/*
 * Copyright © 2021 Kry. All Rights Reserved.
 */
package com.kry.codingtest.servicepoller.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kry.codingtest.servicepoller.entity.Service;
import com.kry.codingtest.servicepoller.exception.ResourceNotFoundException;
import com.kry.codingtest.servicepoller.repository.ServicePollerRepository;
import com.kry.codingtest.servicepoller.util.Status;

@RestController
@RequestMapping("/api")
public class ServicePollerController {

	@Autowired
	ServicePollerRepository servicePollerRepository;

	private static final Logger log = LoggerFactory.getLogger(ServicePollerController.class);

	@GetMapping(path = "/services")
	public ResponseEntity<?> listServices() {
		log.info("ServicesController:  list services");

		List<Service> resource = servicePollerRepository.findAll();
		return ResponseEntity.ok(resource);
	}

	@PostMapping(path = "/add")
	public ResponseEntity<Service> saveService(@Valid @RequestBody Service service) {
		log.info("ServicesController:  add service");
		service.setCreatedAt(new Date());
		service.setStatus(Status.UNKNOWN);
		Service resource = servicePollerRepository.save(service);
		return ResponseEntity.ok(resource);
	}
	
	@DeleteMapping(path = "/delete")
	public void deleteService(@RequestParam String name) throws ResourceNotFoundException {
		log.info("ServicesController:  delete service");
		Service service = servicePollerRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Service not found for this name :: " + name));
		servicePollerRepository.delete(service);
	}
}
