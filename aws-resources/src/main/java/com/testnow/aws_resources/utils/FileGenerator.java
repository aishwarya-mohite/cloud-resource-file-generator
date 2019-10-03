package com.testnow.aws_resources.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.google.gson.Gson;
import com.testnow.aws_resources.service.Aws_resource_service;

/**
 * @author Aishwarya
 *
 *
 */
public class FileGenerator {

	public static void generateRubyFiles(String path) throws IOException {
	String repoPath = path.split("references")[0];
	String json = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

	Aws_resource_service service = new Aws_resource_service();
	Map<String, Object> inputMap = service.jsonToMap(json);

	Object obj = inputMap.get("input");

	Gson gson = new Gson();
	String json1 = gson.toJson(obj);
	Map<String, Object> resourceMap = service.jsonToMap(json1);
	String resourceName = "";
	for (String key : resourceMap.keySet()) {
		System.out.println(key);
		resourceName = key;

	}
	new File(repoPath+"spec/aws_resources/"+resourceName).mkdirs();

	String data1 = "require 'aws-sdk'\n" + "require_relative '../JsonUtilities'\n" + "include JsonUtilities\n"
			+ "\n" + " class " + resourceName.toUpperCase() + "\n" + "    def initialize(value)\n" + "      begin\n"
			+ "      rescue\n" + "         puts \" "+ resourceName +" does not exist\"\n"
			+ "      end	\n" + "    end\n" + "\n" + "    def exists?\n" + "       if @resp.empty?\n"
			+ "        fail \" "+resourceName+" does not exist\"\n" + "       else\n" + "        true\n"
			+ "       end\n" + "    end";
	String data2 = "";

	String data3 = "end\n" + "\n" + " def rean_" + resourceName + "(value)\n\t" + resourceName.toUpperCase()
			+ ".new(value)\n" + "end";

	String json2 = gson.toJson(resourceMap.get(resourceName));
	Map<String, Object> m = service.jsonToMap(json2);
	String tp = "";
	for (String key1 : m.keySet()) {
		tp = key1;

	}
	String json3 = gson.toJson(m.get(tp));
	Map<String, Object> mainMap = service.jsonToMap(json3);
	for (String key2 : mainMap.keySet()) {
		System.out.println(key2);
		data2 = data2 + "\n" + "      def " + key2 + "\n" + "        @resp\n" + "      end";

	}

	String mainPage = data1 + "\n" + data2 + "\n" + data3;
	FileOutputStream out = new FileOutputStream(repoPath+"spec/spec_classes/" + resourceName + ".rb");

	out.write(mainPage.getBytes());
	out.close();

	////////////////////////////////////////////////////////////////////////////////////////////////
	String attrData = "";
	for (String key3 : mainMap.keySet()) {
		attrData = attrData + "\n" + key3.toUpperCase() + "=" + '"' + key3 + '"';

	}

	String attrPage = "module " + resourceName.toUpperCase() + "_ATTR" + attrData + "\n end";

	FileOutputStream out1 = new FileOutputStream(
			repoPath+"spec/aws_resources/" + resourceName + "/" + resourceName + "_attributes.rb");

	out1.write(attrPage.getBytes());
	out1.close();

	////////////////////////////////////////////////////////////////////////////////////////////////
	String specData1 = "require 'spec_helper'\n" + "require 'ResourceType'\n"
			+ "require_relative '../../spec_classes/" + resourceName + "'\n" + "require_relative '" + resourceName
			+ "_attributes'\n" + "\n" + "\n" + "puts \"----------------------------------------------\"\n"
			+ "puts \"Executing awspec for resource " + resourceName + "\"\n"
			+ "puts \"----------------------------------------------\"\n" + "\n" + "" + resourceName
			+ " = getOutputHashByResourceType(ResourceType::" + resourceName.toUpperCase() + ")\n" + "\n" + "if "
			+ resourceName + " != nil\n" + "    " + resourceName + ".each do |name, id|\n" + "		" + resourceName
			+ "_attributes = getAttributesByResourceTypeAndName(ResourceType::" + resourceName.toUpperCase() + ",name)\n"
			+ "		if " + resourceName + "_attributes != nil\n"
			+ "			puts \"---------------------------------------------\"\n"
			+ "			puts \"Validating awspec for " + resourceName + " #{name}\"\n"
			+ "			puts \"---------------------------------------------\"\n" + "			describe rean_"
			+ resourceName + "(id) do\n" + "\n" + "				 		it \"" + resourceName
			+ "  with id:#{id} should exist\" do\n" + "                             should exist\n" + "         \n"
			+ "                         end\n" + "   ";

	String specData2 = "";
	for (String key4 : mainMap.keySet()) {
		specData2 = specData2 + "\n" + "                         if " + resourceName + "_attributes.has_key?("
				+ resourceName.toUpperCase() + "_ATTR::" + key4.toUpperCase() + ") and " + resourceName + "_attributes["
				+ resourceName.toUpperCase() + "_ATTR::" + key4.toUpperCase() + "] != nil\n"
				+ "							its(:" + key4 + ") { should eq value(" + resourceName + "_attributes["
				+ resourceName.toUpperCase() + "_ATTR::" + key4.toUpperCase() + "]) }\n" + "						end";

	}

	String specPage = specData1 + specData2 + "\n			end\n" + "     \n" + "		end\n" + "	end\n" + "end";

	FileOutputStream out2 = new FileOutputStream(
			repoPath+"spec/aws_resources/" + resourceName + "/" + resourceName + "_spec.rb");

	out2.write(specPage.getBytes());
	out2.close();


}
}
