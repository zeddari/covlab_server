 package com.axilog.cov.service.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.dto.representation.InvoicePdfDetail;
import com.axilog.cov.service.InventoryService;
import com.lowagie.text.DocumentException;

@Service
public class PdfServiceQuotation {

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

    @Value("${qrCodeImage}")
    private String qrCodeImage;


    @Value("${logoImage}")
    private String servicesImage;



    @Autowired
    public PdfServiceQuotation(InventoryService inventoryService, SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

//    public Object[] generatePdf(InvoicePdfDetail details ) throws IOException, DocumentException {
//        Context context = getContext(details, "invoicePdfDetail");
//        String html = loadAndFillTemplate(context, "invoice2/pdf_invoice");
//        return new Object[]{html, renderPdf(html, details.getHeader())};
//        //return renderPdf(html, details.getHeader());
//    }

	public Object[] generatePdf(RequestQuotation requestQuotation) throws IOException, DocumentException {
        String sigBase64 = new String(requestQuotation.getSignature());
        Context context = getContext(requestQuotation, "requestQuotation", sigBase64);
        String html = loadAndFillTemplate(context, "invoice2/pdf_quotation");
        return new Object[]{html, renderPdf(html, null)};

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
    public Context getContext(Object objectValue, String objectName, String sigBase64) {
        Context context = new Context();
        context.setVariable(objectName, objectValue);
        context.setVariable("baseUrl", baseUrl);
        context.setVariable("logoImage", logoImage);
        context.setVariable("qrCodeImage", qrCodeImage);
        context.setVariable("footerImage", poFooterImage);
        context.setVariable("headerImage", poHeaderImage);
        context.setVariable("sigBase64", sigBase64);
        return context;
    }

    public String loadAndFillTemplate(Context context, String templateName) {
        return templateEngine.process(templateName, context);
    }



}