package com.axilog.cov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class XlsxFileUtil {

	public static void readFile(String fileLocation) throws IOException {
		FileInputStream file = new FileInputStream(new File(fileLocation));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheetAt(0);

		Map<Integer, List<String>> data = new HashMap<>();
		int i = 0;
		for (Row row : sheet) {
		    data.put(i, new ArrayList<String>());
		    for (Cell cell : row) {
		        switch (cell.getCellTypeEnum()) {
		            case STRING:  break;
		            case NUMERIC: break;
		            case BOOLEAN: break;
		            case FORMULA: break;
		            default: data.get(new Integer(i)).add(" ");
		        }
		    }
		    i++;
		}
	}
	
	
	public static Map<Integer, List<String>> readFileFromStream(InputStream in, int startLine) throws IOException {
		Workbook workbook = new XSSFWorkbook(in);
		Sheet sheet = workbook.getSheetAt(2);

		Map<Integer, List<String>> data = new HashMap<>();
		int i = startLine;
		int rowIdx = 0;
		for (Row row : sheet) {
			if (rowIdx < startLine) {
				rowIdx++;
				continue;
			}
		    data.put(i, new ArrayList<String>());
		    for (Cell cell : row) {
		        switch (cell.getCellTypeEnum()) {
		            case STRING:  
		            	data.get(new Integer(i)).add(cell.getStringCellValue());
		            	break;
		            case NUMERIC: 
		            	data.get(new Integer(i)).add(Double.toString(cell.getNumericCellValue()));
		            	break;
		            case BOOLEAN: 
		            	data.get(new Integer(i)).add(Boolean.toString(cell.getBooleanCellValue())); 
		            	break;
		            case FORMULA: 
		            	data.get(new Integer(i)).add(Double.toString(cell.getNumericCellValue()));
		            	break;
		            default: data.get(new Integer(i)).add("NA");
		        }
		    }
		    i++;
		}
		return data;
	}
}
