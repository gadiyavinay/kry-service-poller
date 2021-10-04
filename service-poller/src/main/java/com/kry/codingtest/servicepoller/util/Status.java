/*
 * Copyright © 2021 Kry. All Rights Reserved.
 */
package com.kry.codingtest.servicepoller.util;

public enum Status {
	OK,
	FAIL,
	UNKNOWN;
	
	@Override
	public String toString() {
		return name().toUpperCase();
	}

}
