/**
 * 
 */
package com.testnow.cloud_resources.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;
import com.testnow.cloud_resources.utils.Utils;

/**
 * @author Aishwarya
 *
 * 
 */
public class FileGeneratorService {

	public static String getSpecType(String path) {
		String repoName = path.split("/")[path.split("/").length - 3];
		String specType = repoName.split("-")[2];
		if (SpecType.awspec.name().equals(specType))
			return "aws";
		else if (SpecType.azurespec.name().equals(specType))
			return "azure";
		else if (SpecType.gcpspec.name().equals(specType))
			return "gcp";
		else
			return null;
	}

	public static Map<String, Object> getAttributesMap(String path) throws IOException {
		String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		Gson gson = new Gson();
		Map<String, Object> inputMap = Utils.jsonToMap(gson.toJson(Utils.jsonToMap(json).get("input")));
		Map<String, Object> resourceMap = Utils
				.jsonToMap(gson.toJson(inputMap.get((String) inputMap.keySet().toArray()[0])));
		return Utils.jsonToMap(gson.toJson(resourceMap.get((String) resourceMap.keySet().toArray()[0])));
	}

	public static String getResourceName(String path) throws IOException {
		String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
		Gson gson = new Gson();
		Map<String, Object> resourceMap = Utils.jsonToMap(gson.toJson(Utils.jsonToMap(json).get("input")));
		String resourceName = (String) resourceMap.keySet().toArray()[0];
		return resourceName;

	}

	public static void writeFile(String pageData, String pathToWriteFile) throws IOException {
		FileOutputStream out1 = new FileOutputStream(pathToWriteFile);
		out1.write(pageData.getBytes());
		out1.close();
	}

}
