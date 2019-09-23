package com.testnow.aws_resources.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Aishwarya
 *
 *
 */
public class Aws_resource_service {

	public static Map<String, Object> jsonToMap(String json) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		ObjectMapper mapper = new ObjectMapper();
		try {
			// Convert Map to JSON
			map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
			});

			// Print JSON output
			System.out.println(map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

}
