package com.angular.angular.boostrapconfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.angular.angular.helper.NodeConfig;

@Component
public class BootstrapConfig {

	public static void writeconfig(String path,String projectName) {
		path=path+projectName;
		String angularJsonPath = path + "/angular.json";
		String bootstrapJsPath = "node_modules/bootstrap/dist/js/bootstrap.bundle.min.js";
		String boostrapcsspath = "node_modules/bootstrap/dist/css/bootstrap.min.css";
	
		try {
			String bootStrapInstallCmd = "npm install bootstrap"; 
			ProcessBuilder pb = NodeConfig.getLocation(path, bootStrapInstallCmd);
			Process process = pb.start();
			if(process.waitFor() == 0) {
				System.out.println("Project Generated");
			}
			else {
				System.out.println("Project Not Generated");
			}
			String jsonContent = new String(Files.readAllBytes(Paths.get(angularJsonPath)));

			JSONObject angularJson = new JSONObject(jsonContent);

			JSONObject buildOptions = angularJson.getJSONObject("projects").getJSONObject(projectName).getJSONObject("architect").getJSONObject("build").getJSONObject("options");
			JSONArray scriptsArray = buildOptions.getJSONArray("scripts");
			JSONArray styleArray = buildOptions.getJSONArray("styles");
			if (!scriptsArray.toString().contains(bootstrapJsPath) && !styleArray.toString().contains(boostrapcsspath)) {
				scriptsArray.put(bootstrapJsPath);
				styleArray.put(boostrapcsspath);
				Files.write(Paths.get(angularJsonPath), angularJson.toString(4).getBytes());
				System.out.println("Bootstrap JavaScript file added to angular.json");
			} else {
				System.out.println("Bootstrap JavaScript file already included in angular.json");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}


//try {
//	ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c",
//			"export PATH=\"$PATH:/Users/sanketkhose/.npm-global/bin\" && " + createNewProjectCommand);
//	pb.directory(new File(basePath));
//
//	Process process = pb.start();
//	if(process.waitFor() == 0) {
//		System.out.println("Project Generated");
//	}
//	else {
//		System.out.println("Project Not Generated");
//	}
//	
//
//} catch (Exception e) {
//	e.printStackTrace();
//}
