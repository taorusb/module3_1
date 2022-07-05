package com.torusb.module3_1.utils;

public enum RecordType {

	CURRENCY("company %s has been changed currency from %s to %s"),
	VOLUME("company %s has been changed volume from %s to %s"),
	LATEST_PRICE("company %s has been changed latest price from %s to %s");

	public final String value;

	RecordType(String value) {
		this.value = value;
	}
}