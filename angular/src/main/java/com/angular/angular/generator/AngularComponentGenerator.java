package com.angular.angular.generator;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.angular.angular.helper.ComponentRemovehelper;
import com.angular.angular.helper.NodeConfig;

@Component
public class AngularComponentGenerator {

	public static void generateAngularComponents(List<String> componentList, List<String> childComponentList,
			List<String> sectionList, String componentFolderPath) {
		try {
			File componentFolder = new File(componentFolderPath);
			if (!componentFolder.exists()) {
				componentFolder.mkdirs();
			}
			Set<String> componentSet = new HashSet<>(componentList);
			Set<String> childComponentSet = new HashSet<>(childComponentList);
			Set<String> sectionComponentSet = new HashSet<>(sectionList);
			for (String component : componentSet) {
				if (component != null && !component.isEmpty()) {

					String createComponentCmd = " ng g c " + component;

					ProcessBuilder pb = NodeConfig.getLocation(componentFolderPath, createComponentCmd);

					Process process = pb.start();
					int exitCode = process.waitFor();
					if (exitCode == 0) {
						System.out.println("Generated Angular component with name " + component);
					} else {
						System.err.println("Failed to generate Angular component with name " + component);
					}
				}
			}
			for (String childComponent : childComponentSet) {

				if (childComponent != null && !childComponent.isEmpty()) {

					String createComponentCmd = " ng g c " + childComponent;

					ProcessBuilder pb = NodeConfig.getLocation(componentFolderPath, createComponentCmd);

					Process process = pb.start();
					int exitCode = process.waitFor();
					if (exitCode == 0) {
						System.out.println("Generated Angular component with name " + childComponent);
					} else {
						System.err.println("Failed to generate Angular component with name " + childComponent);
					}
				}

			}

			for (String sectionComponent : sectionComponentSet) {

				if (sectionComponent != null && !sectionComponent.isEmpty()) {

					String createComponentCmd = " ng g c " + sectionComponent;

					ProcessBuilder pb = NodeConfig.getLocation(componentFolderPath, createComponentCmd);
					Process process = pb.start();
					int exitCode = process.waitFor();
					if (exitCode == 0) {
						System.out.println("Generated Angular component with name " + sectionComponent);
					} else {
						System.err.println("Failed to generate Angular component with name " + sectionComponent);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
