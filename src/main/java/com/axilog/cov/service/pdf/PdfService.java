package com.axilog.cov.service.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.representation.InventoryPdfDetail;
import com.axilog.cov.service.InventoryService;
import com.lowagie.text.DocumentException;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/pdf-resources/";
    private InventoryService inventoryService;
    private SpringTemplateEngine templateEngine;

    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Autowired
    public PdfService(InventoryService inventoryService, SpringTemplateEngine templateEngine) {
        this.inventoryService = inventoryService;
        this.templateEngine = templateEngine;
    }

    public File generatePdf(List<InventoryPdfDetail> details ) throws IOException, DocumentException {
        Context context = getContext(details);
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }


    private File renderPdf(String html) throws IOException, DocumentException {
        File file = File.createTempFile("orders", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        //file.deleteOnExit();
        return file;
    }

    private Context getContext(List<InventoryPdfDetail> details) {
        Context context = new Context();
        
        context.setVariable("inventories", details);
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("templates/po/pdf_orders", context);
    }


}