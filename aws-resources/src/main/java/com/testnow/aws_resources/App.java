package com.testnow.aws_resources;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.testnow.aws_resources.utils.CmdLineArguments;
import com.testnow.aws_resources.utils.CommandLineParser;
import com.testnow.aws_resources.utils.FileGenerator;
import com.testnow.aws_resources.utils.KeyValueCommandLineParser;
import com.testnow.aws_resources.utils.Utils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		try {
			if (args != null && args.length > 0) {

				// Parse command line arguments
				CommandLineParser<Map<String, String>> keyValueParser = new KeyValueCommandLineParser();
				Map<String, String> arguments = keyValueParser.parse(args);

				// Validate passed arguments
				Utils.validateArguments(arguments);

				// Generate input.json
				FileGenerator.generateRubyFiles(arguments.get(CmdLineArguments.INPUT_JSON_FILE_PATH.value()));
			} else {
				System.err.println("Error: Invalid command.");
				Utils.example();
				System.exit(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error while generating files");
			Utils.example();
			System.exit(1);
		}
	}
}
