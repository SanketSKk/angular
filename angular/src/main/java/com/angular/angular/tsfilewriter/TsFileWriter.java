package com.angular.angular.tsfilewriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class TsFileWriter {

	public static void writeInComponentTs(List<String> TAB_ID_LIST, List<String> COMPONENT_LIST,
			List<String> FIELD_NAME_LIST, List<String> typeList, List<String> PLACEHOLDER_LIST,List<String> TYPELIST, String basePath)
			throws IOException {

		Set<String> childComponent = new HashSet<>(COMPONENT_LIST);
		COMPONENT_LIST = new ArrayList<>(childComponent);
		List<String> findVariables = null;
		String line = null;
		List<String> fileContents = null;
		for (int i = 0; i < COMPONENT_LIST.size(); i++) {
			String childComponents = COMPONENT_LIST.get(i);
			TYPELIST.get(i);

			if (!childComponents.isEmpty()) {
				String componentFolderPath = basePath + "/" + childComponents.toLowerCase();
				String htmlFilePath = componentFolderPath + "/" + childComponents.toLowerCase() + ".component.html";
				String tsFilePath = componentFolderPath + "/" + childComponents.toLowerCase() + ".component.ts";

				List<String> htmlFileContents = Files.readAllLines(Paths.get(htmlFilePath));
				List<String> tsFileContents = Files.readAllLines(Paths.get(tsFilePath));
				FileWriter writer = new FileWriter(tsFilePath);

				findVariables = findVariables(htmlFileContents);
				Set<String> childCompone = new HashSet<>(findVariables);
				findVariables = new ArrayList<>(childCompone);

				for (int j = 0; j < tsFileContents.size(); j++) {
//	            	 String event = eventList.get(j);
					line = tsFileContents.get(j);
					if (line.trim().equals("}")) {
						for (String variable : findVariables) {
							tsFileContents.add(j++, String.format("  @Input() %s: string = '';", variable));
						}
						break;
					}
				}

				for (String lines : tsFileContents) {
					writer.write(lines + "\n");
				}
				writer.close();

			}

		}

	}

	private static List<String> findVariables(List<String> fileContents) {
		List<String> variables = new ArrayList<>();
		for (String htmlContent : fileContents) {
			Pattern interpolationPattern = Pattern.compile("\\{\\{([^}]*)\\}\\}");
			Pattern propertyBindingPattern = Pattern.compile("\\[([^\\]]*)\\]");

			// Match interpolation syntax ({{ }}) and extract variables
			Matcher interpolationMatcher = interpolationPattern.matcher(htmlContent);
			while (interpolationMatcher.find()) {
				variables.add(interpolationMatcher.group(1).trim());
			}

			// Match property binding syntax ([ ])
			Matcher propertyBindingMatcher = propertyBindingPattern.matcher(htmlContent);
			while (propertyBindingMatcher.find()) {
				String bindingContent = propertyBindingMatcher.group(1);
				// Extract variable from the binding content
				String[] parts = bindingContent.split("=");
				if (parts.length >= 1) {
					variables.add(parts[0].trim());
				}
			}
		}
		return variables;
	}

}
