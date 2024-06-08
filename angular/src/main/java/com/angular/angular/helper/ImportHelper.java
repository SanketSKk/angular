package com.angular.angular.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.angular.angular.util.ExcelUtil;

@Component
public class ImportHelper {
	
	
	
	public static void updateImport(String basePath, List<String> COMPONENT_LIST) {

		String componentPath = null;
		
		Set<String> componentSet = new HashSet<>(COMPONENT_LIST);

		
		for (String component : componentSet) {
//			String filePath = "/Users/sanketkhose/MY-LAYOUT-LOCAL/src/app/button/button.component.ts";

			componentPath = (basePath + "/" + component.toLowerCase() + "/" + component.toLowerCase() + ".component.ts");

			try {
				addMissingImports(componentPath);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	private static void addMissingImports(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        Set<String> existingImports = new HashSet<>();
        Set<String> componentsInUse = new HashSet<>();

        Pattern importPattern = Pattern.compile("import\\s+\\{\\s*([\\w,\\s]*)\\s*}\\s+from\\s+'([^']+)';");
        Pattern usagePattern = Pattern.compile("\\b(" + String.join("|", ExcelUtil.KNOWN_COMPONENTS.keySet()) + ")\\b");

        // Collect existing imports
        for (String line : lines) {
            Matcher importMatcher = importPattern.matcher(line);
            if (importMatcher.find()) {
                String[] components = importMatcher.group(1).split("\\s*,\\s*");
                existingImports.addAll(Arrays.asList(components));
            }
        }

        // Detect components in use
        for (String line : lines) {
            Matcher usageMatcher = usagePattern.matcher(line);
            while (usageMatcher.find()) {
                componentsInUse.add(usageMatcher.group(1));
            }
        }

        // Determine which components need imports
        existingImports = existingImports.stream().map(String::trim).collect(Collectors.toSet());
        componentsInUse = componentsInUse.stream().map(String::trim).collect(Collectors.toSet());

        componentsInUse.removeAll(existingImports);
        Map<String, String> importsToAdd = new LinkedHashMap<>();
        for (String component : componentsInUse) {
            String importPath = ExcelUtil.KNOWN_COMPONENTS.get(component);
            if (importPath != null) {
                importsToAdd.put(component, importPath);
            }
        }

        if (!importsToAdd.isEmpty()) {
            // Add the missing imports at the top of the file
            List<String> newImports = new ArrayList<>();
            for (Map.Entry<String, String> entry : importsToAdd.entrySet()) {
                newImports.add("import { " + entry.getKey() + " } from '" + entry.getValue() + "';");
            }
            lines.addAll(0, newImports);

            // Write the updated content back to the file
            Files.write(path, lines);
        }
    }

}
