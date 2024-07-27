package com.jtsp.springboot3itextpdf.service;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.jtsp.springboot3itextpdf.handler.HeaderEventHandler;
import com.jtsp.springboot3itextpdf.model.InboundTransaction;
import com.jtsp.springboot3itextpdf.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
public class InboundReportService {

    public static InboundTransaction inboundTransaction = InboundTransaction.builder()
            .id(101L)
            .driverName("John Doe")
            .receiverName("Alice Wong")
            .createdAt(LocalDateTime.now())
            .productList(Arrays.asList(
                    Product.builder().id(1L).name("Air pod").quantity(11L).supplier("Hua Qiang Bei Factory Co. Ltd").build(),
                    Product.builder().id(2L).name("Air Force Dunk").quantity(12L).supplier("Pu Tian Factory Co. Ltd").build(),
                    Product.builder().id(3L).name("Snacks").quantity(13L).supplier("Jiang Xu Deli, Co. Ltd").build()
            ))
            .build();


    public byte[] generateReport() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Add Title
            document.add(new Paragraph("Warehouse Inbound Transaction Document")
                    .setFont(PdfFontFactory.createFont())
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // Add Receiver Information
            document.add(new Paragraph("Receiver & Driver Information")
                    .setFont(PdfFontFactory.createFont())
                    .setFontSize(14)
                    .setBold());

            document.add(new Paragraph("Receiver Name: " + inboundTransaction.getReceiverName()));
            document.add(new Paragraph("Datetime: " + inboundTransaction.getCreatedAt()));
            document.add(new Paragraph("Driver Name: " + inboundTransaction.getDriverName()));

            // Add a line break
            document.add(new Paragraph("\n"));

            // Add Product List
            document.add(new Paragraph("Product List")
                    .setFont(PdfFontFactory.createFont())
                    .setFontSize(14)
                    .setBold());

            // Create table with 3 columns
            Table table = new Table(3);
            table.addHeaderCell(new Cell().add(new Paragraph("Product Name").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Quantity").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Supplier").setBold()));

            // Add dummy data
            for (int i = 0; i < inboundTransaction.getProductList().size(); i++) {
                Product product = inboundTransaction.getProductList().get(i);
                table.addCell(new Cell().add(new Paragraph(product.getName())));
                table.addCell(new Cell().add(new Paragraph(product.getQuantity().toString())));
                table.addCell(new Cell().add(new Paragraph(product.getSupplier())));
            }

            document.add(table);

            String companyName = "Amazon International DC";
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderEventHandler(companyName));

            document.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
