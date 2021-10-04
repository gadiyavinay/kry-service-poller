/*
 * Copyright © 2021 Kry. All Rights Reserved.
 */
package com.kry.codingtest.servicepoller.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kry.codingtest.servicepoller.entity.Service;

public interface ServicePollerRepository extends JpaRepository<Service, Integer> {

	Optional<Service> findByName(String name);
}
