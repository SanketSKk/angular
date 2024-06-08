package com.angular.angular.htmlwriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.angular.angular.helper.SelectorHelper;

@Component
public class HtmlWriter {
	
//	@Autowired
//	public static SelectorHelper selectorHelper;
	
	
	public static void writeInAppHtml(List<String> componentList,List<String> sectionList,List<String> layoutComponent,List<String> listSequence, String basePath) {
		String filePath = basePath + "/app.component.html";		
		TreeMap<Integer, String> sequenseList = new TreeMap<>();
		
		for (int i = 0; i < sectionList.size(); i++) {
			sequenseList.put(Integer.parseInt(listSequence.get(i)), sectionList.get(i));
		}
		
		sectionList = new ArrayList<>(sequenseList.values());
		Set<String> sectionSet = new LinkedHashSet<>(sectionList);
		try (FileWriter fileWriter = new FileWriter(filePath)) {
			for (String componentTag : sectionSet) {
				String selector = SelectorHelper.getSelector(basePath,componentTag);
				fileWriter.write(String.format("<%s> </%s>%n", selector, selector));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeInComponentHtml(List<String> TAB_ID_LIST, List<String> COMPONENT_LIST,
			List<String> FIELD_NAME_LIST, List<String> typeList, List<String> PLACEHOLDER_LIST,
			List<String> LAYOUT_COMPONENT,List<String>  SQ_LIST, String basePath) throws IOException {
		String[] singleEndTags = { "area", "base", "br", "col", "embed", "hr", "img", "input", "keygen", "link", "meta",
				"param", "source", "track", "wbr" };
		

		 Random random = new Random();

		TreeMap<Double, String> sortedList = new TreeMap<>();
		TreeMap<Double, String> placeHolderList = new TreeMap<>();
		TreeMap<Double, String> typeSequeceList = new TreeMap<>();

		for (int i = 0; i < COMPONENT_LIST.size(); i++) {
			sortedList.put(!SQ_LIST.get(i).isEmpty() ?Double.parseDouble(SQ_LIST.get(i)) :Double.parseDouble("0") , FIELD_NAME_LIST.get(i));
		}
		for (int i = 0; i < PLACEHOLDER_LIST.size(); i++) {
			placeHolderList.put(!SQ_LIST.get(i).isEmpty() ?Double.parseDouble(SQ_LIST.get(i)) :Double.parseDouble("0") , PLACEHOLDER_LIST.get(i));
		}
		for (int i = 0; i < typeList.size(); i++) {
			typeSequeceList.put(!SQ_LIST.get(i).isEmpty() ?Double.parseDouble(SQ_LIST.get(i)) :Double.parseDouble("0") , typeList.get(i));
		}
//		LinkedHashMap<Double, String> excellList = new LinkedHashMap<>();
//		int j =1;
//		for (int i = 0; i < COMPONENT_LIST.size(); i++) {
//			excellList.put(!SQ_LIST.get(i).isEmpty() ?j++ :-(random.nextDouble(90) + 10) , FIELD_NAME_LIST.get(i));
//		}
//		
//		LinkedHashMap<Double, String> mappedKeys = new LinkedHashMap<>(sortedList);
//
//        // Iterate through the keys of the first map
//		for (Map.Entry<Double, String> entry : sortedList.entrySet()) {
//			Double key = entry.getKey();
//			String value = entry.getValue();
//			for (Map.Entry<Double, String> entrys : excellList.entrySet()) {
//				Double keys = entrys.getKey();
//				String values = entrys.getValue();
//
//				// If the key exists in the second map, map it with the value from the first map
//				if (keys == key) {
//					excellList.put(key, value);
//				}
//			}
//		}
//
//        // Output the mapped keys
//        System.out.println("Mapped keys: " + excellList);
//
//		LinkedHashMap<Double, String> excellnewlList = new LinkedHashMap<>(excellList);

		ArrayList<String> sectionList = new ArrayList<>(sortedList.values());
		ArrayList<String> placeholderList = new ArrayList<>(placeHolderList.values());
		ArrayList<String> typeeList = new ArrayList<>(typeSequeceList.values());

		
//		for (Map.Entry<Double, String> entry : excellList.entrySet()) {
//            Double key = entry.getKey();
//            if (sortedList.containsKey(key)) {
//                String value = sortedList.get(key);
//                excellnewlList.put(key, value);
//            }
//        }
		
		Set<String> writtenFiles = new HashSet<>(); // Keep track of written files

		boolean createNewFile = true; // Flag to indicate whether to create a new file
		
		
//6
		for (int i = 0; i < TAB_ID_LIST.size(); i++) {
			String component = TAB_ID_LIST.get(i);
			String childComponent = COMPONENT_LIST.get(i);
			String fieldName = sectionList.get(i);
//			String src = srcList.get(i);
//			String height = heightList.get(i);
			String type = typeeList.get(i);
			String placeHolder = placeholderList.get(i);
//			String name = nameList.get(i);
//			String action = actionList.get(i);
			String componentPath = null;
			if (!component.isEmpty() || !childComponent.isEmpty()) {
				componentPath = (basePath + "/" + (component.isEmpty() ? childComponent.toLowerCase() : component.toLowerCase()) + "/"
						+ (component.isEmpty() ? childComponent.toLowerCase() : component.toLowerCase()) + ".component.html");

				 childComponent = SelectorHelper.getSelector(basePath,childComponent);
				

				// Add the path to the set of written files
				if (writtenFiles.contains(componentPath)) {
					createNewFile = false; // Reset the flag
				}
				writtenFiles.add(componentPath);
				FileWriter writer;
				if (createNewFile) {
					writer = new FileWriter(componentPath); // Create a new file
				} else {
					writer = new FileWriter(componentPath, true); // Append mode if file exists
				}

				boolean isSingleTag = false;
				
				if(type.equalsIgnoreCase("label")) {
					writer.write(String.format("<%s>"+fieldName +"</%s><br>\n", type,type));
					
				}
				
				if (!component.isEmpty() && !childComponent.isEmpty() && !isSingleTag && !placeHolder.isEmpty()) {
					writer.write(String.format("<%s [type]=\"'%s'\" [placeholder]=\"'%s'\"></%s><br>\n", childComponent, type,placeHolder, childComponent));
					
				}
				
				if(childComponent.contains("button")) {
					writer.write(String.format("<%s [type]=\"'%s'\" ></%s>\n",childComponent, type,  childComponent));
				}
				writer.close();
				System.out.println("Written to file: " + componentPath); // Tracing
			}
		}
	}
	
	public static void writeInComponentchildHtml(List<String> TAB_ID_LIST, List<String> COMPONENT_LIST,
			List<String> FIELD_NAME_LIST, List<String> typeList, List<String> PLACEHOLDER_LIST, List<String> SECTION,
			List<String> LAYOUT_COMPONENT, List<String> iS_VISIBLE_LIST, List<String> uRL_LIST,
			List<String> cLASSES_LIST, List<String> vALUE_LIST,List<String> LAYOUT_SEQ_LIST, String basePath) throws IOException {

		String[] singleEndTags = { "area", "base", "br", "col", "embed", "hr", "img", "input", "keygen", "link", "meta",
				"param", "source", "track", "wbr" };

		Set<String> writtenFiles = new HashSet<>(); // Keep track of written files
		Set<String> component = new HashSet<>(COMPONENT_LIST); // Keep track of written files
		
		updateLayout(SECTION, LAYOUT_COMPONENT,uRL_LIST,cLASSES_LIST,vALUE_LIST,LAYOUT_SEQ_LIST, basePath);

		
        List<String> uniqueValuesList = new ArrayList<>(component);

		boolean createNewFile = true; // Flag to indicate whether to create a new file

		Set<String> writtenFile = new HashSet<>(); // Keep track of written files
		for (int i = 0; i < uniqueValuesList.size(); i++) {
			String childComponent = uniqueValuesList.get(i);
			String type = typeList.get(i);
			String placeHolder = PLACEHOLDER_LIST.get(i);
			String componentPath = null;
			
			if (!childComponent.toLowerCase().isEmpty() && !writtenFile.contains(childComponent.toLowerCase())) {
				componentPath = (basePath + "/" + childComponent.toLowerCase() + "/" + childComponent.toLowerCase() + ".component.html");
				writtenFile.add(childComponent.toLowerCase());


				// Add the path to the set of written files
				if (writtenFiles.contains(componentPath)) {
					createNewFile = false; // Reset the flag

				}
				writtenFiles.add(componentPath);
				FileWriter writer;
				if (createNewFile) {
					writer = new FileWriter(componentPath); // Create a new file
//                createNewFile = false; // Reset the flag
				} else {
					writer = new FileWriter(componentPath, true); // Append mode if file exists
				}

				boolean isSingleTag = false;

				if (isSingleEndTag(singleEndTags, childComponent.toLowerCase())) {
					if (!childComponent.isEmpty()) {
						writer.write(String.format("<%s type={{type}} [placeholder]=\"placeholder\">\n", childComponent.toLowerCase()));
						isSingleTag = true;
					}
				}

				if (!childComponent.isEmpty() && !isSingleTag && !placeHolder.isEmpty()) {
					writer.write(String.format("<%s [type]=\"'%s'\" [placeholder]=\"'%s'\"></%s>\n", childComponent, type,placeHolder, childComponent));
				}

				if (childComponent.equalsIgnoreCase("button") ) { // button button Login alert - Subimt																// Successfully
					writer.write(String.format("<%s type=\"{{type}}\">{{type}}</%s>\n",childComponent.toLowerCase(), childComponent.toLowerCase()));
				}
				writer.close();
				System.out.println("Written to file: " + componentPath); // Tracing
			}
		}

	}
	
	private static void updateLayout(List<String> sECTION, List<String> lAYOUT_COMPONENT, List<String> uRL_LIST, List<String> cLASSES_LIST, List<String> vALUE_LIST, List<String> LAYOUT_SEQ_LIST, String basePath) throws IOException {
		
		for (int i = 0; i < sECTION.size(); i++) {
			String section = sECTION.get(i);
			String layout = lAYOUT_COMPONENT.get(i);
			String url = uRL_LIST.get(i);
			String cLASSES = cLASSES_LIST.get(i);
			String value = vALUE_LIST.get(i);
			String filePat = basePath + "/" + section.toLowerCase() + "/" + section.toLowerCase() + ".component.html";
			if (!url.isEmpty() && !cLASSES.isEmpty()) {

				try (FileWriter fileWriter = new FileWriter(filePat)) {
					// Fetch the HTML content of the Bootstrap documentation page
					Document doc = Jsoup.connect(url).get();
					Element nabars = doc.selectFirst(cLASSES);//
					System.out.println(nabars);
					String html = nabars.outerHtml();
					fileWriter.write(String.format(html));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!layout.isEmpty()) {
//				String filePath = basePath + "/" + section.toLowerCase() + "/" + section.toLowerCase()
//						+ ".component.html";
//				FileWriter writer = new FileWriter(filePath);

				System.out.println();

				try (FileWriter fileWriter = new FileWriter(filePat)) {
					String selector = SelectorHelper.getSelector(basePath, layout.toLowerCase());
					fileWriter.write(String.format("<%s> </%s>%n", selector, selector));

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(!value.isEmpty()) {
				try (FileWriter fileWriter = new FileWriter(filePat)) {
					String selector = SelectorHelper.getSelector(basePath, layout.toLowerCase());
					fileWriter.write(String.format("<footer> %s </footer>%n", value));
//					<footer> © 2022 Company, Inc</footer>

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		

		
	}

	private static boolean isSingleEndTag(String[] singleEndTags, String htmlElement) {
		return Arrays.asList(singleEndTags).contains(htmlElement.toLowerCase());
	}

}
