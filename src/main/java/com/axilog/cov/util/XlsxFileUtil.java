package com.axilog.cov.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.axilog.cov.dto.representation.HeaderPdfDetail;
import com.axilog.cov.dto.representation.InventoryPdfDetail;
import com.axilog.cov.dto.representation.PoPdfDetail;



public class XlsxFileUtil {
	public XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private PoPdfDetail poPdfDetail;
    
    public XlsxFileUtil( PoPdfDetail poPdfDetail) {
        this.poPdfDetail = poPdfDetail;
        workbook = new XSSFWorkbook();
    }
    
    public void writeHeaderLine(PoPdfDetail poPdfDetail) {
        sheet = workbook.createSheet("po_create");
        HeaderPdfDetail headerPdfDetail =  poPdfDetail.getHeaderPdfDetail();   
        String outlet = poPdfDetail.getOutlet();
       
        Row row1 = sheet.createRow(0);
        Row row2 = sheet.createRow(1);
        Row row3 = sheet.createRow(2);
        Row row4 = sheet.createRow(3);
        Row row5 = sheet.createRow(5);
        
        CellStyle style = workbook.createCellStyle();
        CellStyle styleheader = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        XSSFFont fontheader = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        fontheader.setBold(true);
        fontheader.setFontHeight(14);
        style.setFont(font);
        styleheader.setFont(fontheader);
        styleheader.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        styleheader.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        styleheader.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        styleheader.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        styleheader.setAlignment(HorizontalAlignment.CENTER);
        createCell(row1, 0, "Order #:", style);      
        createCell(row1, 1, headerPdfDetail.getOrderNumber(), style);       
        createCell(row1, 2, "Vendor:", style);    
        createCell(row1, 3,  headerPdfDetail.getVendor(), style);
        
        createCell(row2, 0, "Created :", style);      
        createCell(row2, 1, headerPdfDetail.getCreationDate(), style);       
        createCell(row2, 2, "Contact Person:", style);    
        createCell(row2, 3,  headerPdfDetail.getContactPersonName(), style);
        
        createCell(row3, 0, "Due Date:", style);      
        createCell(row3, 1, headerPdfDetail.getDueDate(), style);       
        createCell(row3, 2, "Mobile:", style);    
        createCell(row3, 3,  headerPdfDetail.getContactPersonMobile(), style);
        
        createCell(row4, 0, "Destination:", style);      
        createCell(row4, 1, outlet, style);       
        createCell(row4, 2, "Email:", style);    
        createCell(row4, 3,  headerPdfDetail.getContactPersonEmail(), style);
        
        createCell(row5, 0, "Product Code", styleheader);      
        createCell(row5, 1, "Product Description", styleheader);       
        createCell(row5, 2, "Category", styleheader);    
        createCell(row5, 3,  "Quantity", styleheader);
        createCell(row5, 4,  "UOM", styleheader);       

      }
 

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
    
    @SuppressWarnings("deprecation")
	public void writeDataLines(PoPdfDetail poPdfDetail) {
    	List<InventoryPdfDetail> listPo= poPdfDetail.getListDetails();
    
    	int rowCount = 6;
   	 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(12);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
       
        for (InventoryPdfDetail po : listPo) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, po.getCode(), style);
            createCell(row, columnCount++, po.getDescription(), style);
            createCell(row, columnCount++, po.getCategory(), style);
            createCell(row, columnCount++, po.getQuantity(), style);
            createCell(row, columnCount++, po.getUom(), style);
		   
             
        }
   }
   
  

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
		            	cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
		                Double doubleValue = cell.getNumericCellValue();
		                BigDecimal bd = new BigDecimal(doubleValue.toString());
		                Long lonVal = bd.longValue();
		            	data.get(new Integer(i)).add(Long.toString(lonVal));
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
