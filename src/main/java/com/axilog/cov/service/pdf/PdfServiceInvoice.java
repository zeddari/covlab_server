package com.axilog.cov.service.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.axilog.cov.dto.representation.InvoicePdfDetail;
import com.axilog.cov.service.InventoryService;
import com.lowagie.text.DocumentException;

@Service
public class PdfServiceInvoice {

    private static final String PDF_RESOURCES = "/";
    private SpringTemplateEngine templateEngine;
   
    @Value("${baseUrl}")
    private String baseUrl;
    
    @Value("${poFooterImage}")
    private String poFooterImage;
    
    @Value("${poHeaderImage}")
    private String poHeaderImage;
    
    @Value("${logoImage}")
    private String logoImage;
    


    
    @Autowired
    public PdfServiceInvoice(InventoryService inventoryService, SpringTemplateEngine templateEngine) {
        this.inventoryService = inventoryService;
        this.templateEngine = templateEngine;
    }

    public Object[] generatePdf(InvoicePdfDetail details ) throws IOException, DocumentException {
        Context context = getContext(details, "invoicePdfDetail");
        String html = loadAndFillTemplate(context, "invoice2/pdf_invoice");
        return new Object[]{html, renderPdf(html, details.getHeader())};
        //return renderPdf(html, details.getHeader());
    }


    private File renderPdf(String html, String invoice) throws IOException, DocumentException {
        File file = new File("invoice_"+invoice+".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    
    /**
     * @param objectValue
     * @param objectName
     * @return
     */
    public Context getContext(Object objectValue, String objectName) {
        Context context = new Context();
        context.setVariable(objectName, objectValue);
        context.setVariable("baseUrl", baseUrl);
        context.setVariable("logoImage", logoImage);
        context.setVariable("footerImage", poFooterImage);
        context.setVariable("headerImage", poHeaderImage);
        return context;
    }

    public String loadAndFillTemplate(Context context, String templateName) {
        return templateEngine.process(templateName, context);
    }
    
}