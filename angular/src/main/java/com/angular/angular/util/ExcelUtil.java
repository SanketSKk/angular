package com.angular.angular.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtil {
	
	
	
    public static final Map<String, String> KNOWN_COMPONENTS = new HashMap<>();
    
    static {
        KNOWN_COMPONENTS.put("Input", "@angular/core");
        KNOWN_COMPONENTS.put("Component", "@angular/core");
        KNOWN_COMPONENTS.put("NgModule", "@angular/core");
        KNOWN_COMPONENTS.put("Injectable", "@angular/core");
        // Add more known components and their import paths as needed
    }
    
//	public static final String CANCELLED = "CANCELLED";
    
    public static String getLayoutCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return String.valueOf((int)cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}
	
    public static String getFieldCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		default:
			return "";
		}
	}



}
