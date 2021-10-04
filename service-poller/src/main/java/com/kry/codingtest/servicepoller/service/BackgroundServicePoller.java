/*
 * Copyright © 2021 Kry. All Rights Reserved.
 */
package com.kry.codingtest.servicepoller.service;

import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kry.codingtest.servicepoller.controller.ServicePollerController;
import com.kry.codingtest.servicepoller.repository.ServicePollerRepository;
import com.kry.codingtest.servicepoller.util.Status;

@Component
public class BackgroundServicePoller {

	@Autowired
	ServicePollerRepository servicePollerRepository;
	
	private static final Logger log = LoggerFactory.getLogger(ServicePollerController.class);

	@Scheduled(fixedDelay = 5000)
	public void pollServices() {
		log.info("Running background poller service");

		servicePollerRepository.findAll().forEach(service -> {
			try {
			URL url = new URL(service.getUrl());
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setConnectTimeout(1000);			
			
			if(connection.getResponseCode() != 200 || connection == null) {
				service.setStatus(Status.FAIL);
			}
			else {
				service.setStatus(Status.OK);
			}
			}
			catch(Exception e) {
				service.setStatus(Status.FAIL);
				e.printStackTrace();
			}
			servicePollerRepository.save(service);
		});
	}

}
