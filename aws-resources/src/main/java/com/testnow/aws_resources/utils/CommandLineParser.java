package com.testnow.aws_resources.utils;

public interface CommandLineParser <T> {
	public T parse(String[] args);
}
