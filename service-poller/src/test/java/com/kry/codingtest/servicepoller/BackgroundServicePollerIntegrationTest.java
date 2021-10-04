/*
 * Copyright © 2021 Kry. All Rights Reserved.
 */
package com.kry.codingtest.servicepoller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.kry.codingtest.servicepoller.entity.Service;
import com.kry.codingtest.servicepoller.repository.ServicePollerRepository;
import com.kry.codingtest.servicepoller.service.BackgroundServicePoller;
import com.kry.codingtest.servicepoller.util.Status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BackgroundServicePollerIntegrationTest {
	
	@SpyBean
	private BackgroundServicePoller poller;
	
	@Autowired
	private ServicePollerRepository repo;
	
	@Test
    public void whenWaitOneMinute_thenScheduledIsCalledAtLeastOneTimes() throws InterruptedException {
		Thread.sleep(5000);
		Awaitility.await()
        .atMost(Duration.FIVE_SECONDS)
        .untilAsserted(() -> verify(poller, atLeast(1)).pollServices());
    }
	
	@Test
	public void testpollServicesForOkStatus() throws InterruptedException {
		Service service = new Service();
		service.setName("Kry");
		service.setUrl("https://www.kry.se");
		service.setStatus(Status.OK);
		service.setCreatedAt(new Date());
		repo.save(service);
		Thread.sleep(5000);
		Awaitility.await()
        .atMost(Duration.FIVE_SECONDS)
        .untilAsserted(() -> assertEquals(Status.OK, repo.findByName("Kry").get().getStatus()));
		
	}
	
	@Test
	public void testpollServicesForFailStatus() throws InterruptedException {
		Service service = new Service();
		service.setName("KryFail");
		service.setUrl("https://www.kri.se");
		service.setStatus(Status.UNKNOWN);
		service.setCreatedAt(new Date());
		repo.save(service);
		Thread.sleep(5000);
		Awaitility.await()
        .atMost(Duration.FIVE_SECONDS)
        .untilAsserted(() -> assertEquals(Status.FAIL, repo.findByName("KryFail").get().getStatus()));
		
	}
	
}
