package com.testnow.cloud_resources.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

/**
 * @author Aishwarya
 *
 *
 */
public class FileGenerator {
	
	FileGeneratorService service = new FileGeneratorService();

	public static  void generateRubyFiles(String path) throws IOException {
		String repoPath = path.split("references")[0];

		String specType = FileGeneratorService.getSpecType(path);
		String resourceName = FileGeneratorService.getResourceName(path);
		Map<String, Object> mainMap = FileGeneratorService.getAttributesMap(path);
	
		new File(repoPath+"spec/"+specType+"_resources/"+resourceName).mkdirs();
		specClass(resourceName,repoPath,mainMap);
		attributeClass(specType,resourceName,repoPath,mainMap);
		resourceSpecClass(specType,resourceName,repoPath,mainMap);
		System.out.println("Files created for : "+resourceName);
		String text = "\n\t"+resourceName.toUpperCase()+"=\""+resourceName+"\"\nend";
		try {
			RandomAccessFile f = new RandomAccessFile(new File(repoPath + "/spec/ResourceType.rb"), "rw");
			f.seek(f.length() - 4); // before end
			f.write(text.getBytes());
			f.close();
		} catch (IOException e) {
		}
	}
	
	public static void specClass (String resourceName, String repoPath, Map<String,Object> mainMap) throws IOException {
		String data1 = "  require_relative '../Util'\n" + 
				"  include Util\n" + 
				"\n" + 
				"  class "+resourceName.toUpperCase()+ "\n" + 
				"    def initialize(value)\n" + 
				"      begin    \n" + 
				"      rescue\n" + 
				"      puts \"The "+resourceName+" does not exist\"\n" + 
				"    end\n" + 
				"    end\n" + 
				"\n" + 
				"    def exists?\n" + 
				"      if defined?(@response) == nil || @response.nil?\n" + 
				"       fail \"The "+resourceName+" does not exist\"\n" + 
				"      else\n" + 
				"      true\n" + 
				"      end\n" + 
				"    end";
		String data2 = "";
		for (String key : mainMap.keySet()) {
			System.out.println(key);
			data2 = data2 + "\n" + "    def " + key + "\n" + "      @resp\n" + "    end";

		}
		String data3 = "end\n" + 
				"  def hcap_"+resourceName+"(value)\n" + 
				"      "+resourceName.toUpperCase()+".new(value)\n" + 
				"  end";
		
		String mainData = data1 + "\n" + data2 + "\n" + data3;
		String path = repoPath+"spec/spec_classes/" + resourceName + ".rb";
		FileGeneratorService.writeFile(mainData, path);
	}
	
	public static void attributeClass (String specType , String resourceName, String repoPath, Map<String,Object> mainMap) throws IOException {
		String data1 = "";
		for (String key : mainMap.keySet()) {
			System.out.println(key);
			data1 = data1 + "\n\t" + key.toUpperCase() + "=" + '"' + key + '"';

		}
		String mainData = "module " + resourceName.toUpperCase() + "_ATTR" + data1 + "\n end";
		String path = repoPath+"spec/"+specType+"_resources/" + resourceName + "/" + resourceName + "_attributes.rb";
		FileGeneratorService.writeFile(mainData, path);
	}
	
	public static void resourceSpecClass (String specType , String resourceName, String repoPath, Map<String,Object> mainMap) throws IOException {
		String data1 = "require 'spec_helper'\n" + 
				"require 'ResourceType'\n" + 
				"require_relative '../../spec_classes/"+resourceName+"'\n" + 
				"require_relative '"+resourceName+"_attributes'\n" + 
				"\n" + 
				"puts \"-------------------------------------------\"\n" + 
				"puts \"Executing "+specType+" spec for resource - "+resourceName+"\"\n" + 
				"puts \"-------------------------------------------\"\n" + 
				"\n" + 
				"list_of_"+specType+"_resource = getOutputHashByResourceType(ResourceType::"+resourceName.toUpperCase()+")\n" + 
				"\n" + 
				"if list_of_"+specType+"_resource != nil\n" + 
				"\n" + 
				"  list_of_"+specType+"_resource.each {|name, value|\n" + 
				"\n" + 
				"    puts \"#{name} : #{value}\"\n" + 
				"    "+resourceName+"_attributes = getAttributesByResourceTypeAndName(ResourceType::"+resourceName.toUpperCase()+", name)\n" + 
				"    puts \""+resourceName+" attributes : #{"+resourceName+"_attributes}\"\n" + 
				"\n" + 
				"    if "+resourceName+"_attributes != nil \n" + 
				"\n" + 
				"      puts \"--------------------------------------------\"\n" + 
				"      puts \"Validating "+specType+" spec for resource - "+resourceName+" : #{name}\"\n" + 
				"      puts \"--------------------------------------------\"\n" + 
				"\n" + 
				"      describe hcap_"+resourceName+"(value) do";
		String data2 = "";
		for (String key4 : mainMap.keySet()) {
			data2 = data2 + "\n" + "\t\tif " + resourceName + "_attributes.has_key?("
					+ resourceName.toUpperCase() + "_ATTR::" + key4.toUpperCase() + ") and " + resourceName + "_attributes["
					+ resourceName.toUpperCase() + "_ATTR::" + key4.toUpperCase() + "] != nil\n"
					+ "\t\t\tits(:" + key4 + ") { should eq value(" + resourceName + "_attributes["
					+ resourceName.toUpperCase() + "_ATTR::" + key4.toUpperCase() + "]) }\n" + "\t\tend";

		}
		String data3 = "      end\n" + 
				"\n" + 
				"    end\n" + 
				"  }\n" + 
				"\n" + 
				"end";
		String mainData = data1 + "\n" + data2 + "\n" + data3;
		String path = repoPath+"spec/"+specType+"_resources/" + resourceName + "/" + resourceName + "_spec.rb";
		FileGeneratorService.writeFile(mainData, path);
	}
	
}
