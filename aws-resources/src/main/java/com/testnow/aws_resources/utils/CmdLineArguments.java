package com.testnow.aws_resources.utils;

public enum CmdLineArguments {
	
	INPUT_JSON_FILE_PATH("--input-json-file-path");
	
	private final String cmd;

	private CmdLineArguments(String cmd) {
		this.cmd = cmd;
	}

	public String value() {
		return this.cmd;
	}
}