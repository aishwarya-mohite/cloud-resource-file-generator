package com.testnow.cloud_resources.utils;

public interface CommandLineParser <T> {
	public T parse(String[] args);
}
