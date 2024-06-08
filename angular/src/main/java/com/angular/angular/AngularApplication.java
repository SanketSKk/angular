package com.angular.angular;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.angular.angular.boostrapconfig.BootstrapConfig;
import com.angular.angular.generator.AngularComponentGenerator;
import com.angular.angular.generator.AngularProjectGenerator;
import com.angular.angular.helper.ImportHelper;
import com.angular.angular.htmlwriter.HtmlWriter;
import com.angular.angular.tsfilewriter.TsFileWriter;
import com.angular.angular.util.ExcelUtil;

@SpringBootApplication
public class AngularApplication {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {

		SpringApplication.run(AngularApplication.class, args);
		String basepath = "/Users/sanketkhose/";
		String projectName = "MY-LAYOUT-LOCAL";
		String projectPath = basepath + projectName;

		String rootbasePath = projectPath + "/src/app";

		// FIELD ATTRIBUTES
		List<String> MENU_ID_LIST = new ArrayList<>();
		List<String> TAB_ID_LIST = new ArrayList<>();
		List<String> FIELD_NAME_LIST = new ArrayList<>();
		List<String> TYPE_LIST = new ArrayList<>();
		List<String> COMPONENT_LIST = new ArrayList<>();
		List<String> PLACEHOLDER_LIST = new ArrayList<>();
		List<String> FIELD_SEQ_LIST = new ArrayList<>();
		List<String> FIELD_ID_LIST = new ArrayList<>();
		List<String> D_ROW_LIST = new ArrayList<>();
		List<String> D_COLUMN_LIST = new ArrayList<>();
		List<String> DATA_TYPE_LIST = new ArrayList<>();

		// LAYOUT ATTRIBUTES
//		PAGE_ID	LAYOUT_SEQ	LAYOUT_ID	SECTIONS	COMPONENTS	IS_VISIBLE
		List<String> PAGE_ID_LIST = new ArrayList<>();
		List<String> LAYOUT_SEQ_LIST = new ArrayList<>();
		List<String> LAYOUT_ID_LIST = new ArrayList<>();
		List<String> SECTIONS_LIST = new ArrayList<>();
		List<String> COMPONENTS_LIST = new ArrayList<>();
		List<String> LAYOUT_COMPONENTS_LIST = new ArrayList<>();
		List<String> IS_VISIBLE_LIST = new ArrayList<>();
		List<String> VALUE_LIST = new ArrayList<>();
		List<String> URL_LIST = new ArrayList<>();
		List<String> CLASSES_LIST = new ArrayList<>();

		try {
			FileInputStream file = new FileInputStream(
					new File("/Users/sanketkhose/Documents/Local_Input_Angular_Acceleration_Layout.xlsx"));
			Workbook workbook = WorkbookFactory.create(file);
			Sheet field = workbook.getSheet("FIELD");
			Sheet layout = workbook.getSheet("LAYOUT");

			for (int i = 1; i <= field.getLastRowNum(); i++) {
				Row row = field.getRow(i);
				if (row != null) {
					MENU_ID_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(0)));
					TAB_ID_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(1)));
					FIELD_SEQ_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(2)));
					FIELD_ID_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(3)));
					FIELD_NAME_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(4)));
					D_ROW_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(5)));
					D_COLUMN_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(6)));
					TYPE_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(7)));
					COMPONENT_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(8)));
					PLACEHOLDER_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(9)));
					DATA_TYPE_LIST.add(ExcelUtil.getFieldCellValue(row.getCell(10)));
				}
			}

			for (int i = 1; i < layout.getLastRowNum(); i++) {
				Row row = layout.getRow(i);
				if (row != null) {
					PAGE_ID_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(0)));
					LAYOUT_SEQ_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(1)));
					LAYOUT_ID_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(2)));
					SECTIONS_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(3)));
					LAYOUT_COMPONENTS_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(4)));
					IS_VISIBLE_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(5)));
					VALUE_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(6)));
					URL_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(7)));
					CLASSES_LIST.add(ExcelUtil.getLayoutCellValue(row.getCell(8)));
				}
			}
			workbook.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		AngularProjectGenerator.generateAngularProject(projectName, basepath);

		BootstrapConfig.writeconfig(basepath, projectName);

		AngularComponentGenerator.generateAngularComponents(TAB_ID_LIST, COMPONENT_LIST, SECTIONS_LIST, rootbasePath);

		HtmlWriter.writeInAppHtml(TAB_ID_LIST, SECTIONS_LIST, LAYOUT_COMPONENTS_LIST, LAYOUT_SEQ_LIST, rootbasePath);

		HtmlWriter.writeInComponentHtml(TAB_ID_LIST, COMPONENT_LIST, FIELD_NAME_LIST, TYPE_LIST, PLACEHOLDER_LIST,
				LAYOUT_COMPONENTS_LIST, FIELD_SEQ_LIST, rootbasePath);
		HtmlWriter.writeInComponentchildHtml(TAB_ID_LIST, COMPONENT_LIST, FIELD_NAME_LIST, TYPE_LIST, PLACEHOLDER_LIST,
				SECTIONS_LIST, LAYOUT_COMPONENTS_LIST, IS_VISIBLE_LIST, URL_LIST, CLASSES_LIST, VALUE_LIST,
				LAYOUT_SEQ_LIST, rootbasePath);

		TsFileWriter.writeInComponentTs(TAB_ID_LIST, COMPONENT_LIST, FIELD_NAME_LIST, TYPE_LIST, PLACEHOLDER_LIST,
				DATA_TYPE_LIST, rootbasePath);
		ImportHelper.updateImport(rootbasePath, COMPONENT_LIST);

		System.out.println("project is created");

	}
	
	
	
	
}
