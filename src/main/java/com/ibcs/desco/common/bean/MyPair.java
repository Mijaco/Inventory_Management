package com.ibcs.desco.common.bean;

public class MyPair {
	private final String key;
	private final Double value;

	public MyPair(String aKey, Double aValue) {
		key = aKey;
		value = aValue;
	}

	public String key() {
		return key;
	}

	public Double value() {
		return value;
	}
}
