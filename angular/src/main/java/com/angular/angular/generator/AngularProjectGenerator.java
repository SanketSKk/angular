package com.angular.angular.generator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.angular.angular.helper.ComponentRemovehelper;
import com.angular.angular.helper.NodeConfig;

public class AngularProjectGenerator {

	public static void generateAngularProject(String projectName, String basePath) {
		
		
		String projectDir = basePath + "" + projectName;
		Path projectPath = Paths.get(projectDir);
		if (Files.exists(projectPath)) {
			// If the directory exists, delete the existing AngularAccelerator project
			try {
				ComponentRemovehelper.deleteDirectory(projectPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String createNewProjectCommand = "ng new " + projectName + " --routing --style scss";
		try {
			ProcessBuilder pb = NodeConfig.getLocation(basePath, createNewProjectCommand);
			Process process = pb.start();

//            // Handle process output and errors (optional)
//            new Thread(() -> {
//                try (java.io.BufferedReader reader = new java.io.BufferedReader(
//                        new java.io.InputStreamReader(process.getInputStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.out.println(line);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//
//            new Thread(() -> {
//                try (java.io.BufferedReader reader = new java.io.BufferedReader(
//                        new java.io.InputStreamReader(process.getErrorStream()))) {
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        System.err.println(line);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();

			int exitCode = process.waitFor();
			System.out.println("Process exited with code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
