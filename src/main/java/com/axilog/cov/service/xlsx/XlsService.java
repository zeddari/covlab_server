package com.axilog.cov.service.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.axilog.cov.dto.representation.PoPdfDetail;
import com.axilog.cov.util.XlsxFileUtil;

@Service
public class XlsService {

    @Value("${baseUrl}")
    private String baseUrl;
    
    @Value("${poFooterImage}")
    private String poFooterImage;
    
    @Value("${poHeaderImage}")
    private String poHeaderImage;

    public File exportExcel(PoPdfDetail poPdfDetail) throws IOException {
    	XlsxFileUtil excelExporter = new XlsxFileUtil(poPdfDetail);
    	excelExporter.writeHeaderLine(poPdfDetail);
    	excelExporter.writeDataLines(poPdfDetail);
        FileOutputStream outputStream = new FileOutputStream("order"+poPdfDetail.getHeaderPdfDetail().getDestination()+".xlsx");
        excelExporter.workbook.write(outputStream);
        excelExporter.workbook.close();
        outputStream.close();
        return new File("order"+poPdfDetail.getHeaderPdfDetail().getDestination()+".xlsx") ;
    	}


}