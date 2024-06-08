package com.angular.angular.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component
public class SelectorHelper {
	
	
	public static String getSelector(String basePath, String childComponent) {
		if (!childComponent.isEmpty()) {
			File file = new File(basePath + "/" + childComponent.toLowerCase() + "/" + childComponent.toLowerCase() + ".component.ts");
			if (file.exists()) {
				try (Scanner scanner = new Scanner(file)) {
					while (scanner.hasNextLine()) {
						String line = scanner.nextLine();
						if (line.contains("selector")) {
							childComponent = line.substring(line.indexOf(':') + 2).replaceAll("[',]", "").trim();
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
//					System.out.println("File does not exist: " + filePath);
			}
		}
		return childComponent;
	}

}
