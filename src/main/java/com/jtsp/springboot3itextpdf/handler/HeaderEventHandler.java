package com.jtsp.springboot3itextpdf.handler;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Component;


public class HeaderEventHandler implements IEventHandler {
    private String headerText;

    public HeaderEventHandler(String headerText) {
        this.headerText = headerText;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDoc = docEvent.getDocument();
        PdfPage page = docEvent.getPage();
        Rectangle pageSize = page.getPageSize();
        float topMargin = 20;
        float rightMargin = 10;
        float textTopMargin = 0;

        PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
        Rectangle headerArea = new Rectangle(pageSize.getLeft(), pageSize.getTop() - topMargin, pageSize.getWidth(), 20);
        Canvas canvas = new Canvas(pdfCanvas, headerArea, true);

        canvas.setFontSize(12);
        Text headerContext = new Text("Amazon Super Center Warehouse").setItalic();
        canvas.showTextAligned(new Paragraph(headerContext),
                pageSize.getRight() - rightMargin,
                pageSize.getTop() - topMargin + textTopMargin,
                TextAlignment.RIGHT);

        // Draw horizontal line
        float lineY = pageSize.getTop() - topMargin - 5; // Adjust the -5 to position the line as needed
        pdfCanvas.setStrokeColor(new DeviceRgb(0, 0, 0));
        pdfCanvas.moveTo(pageSize.getLeft(), lineY);
        pdfCanvas.lineTo(pageSize.getRight(), lineY);
        pdfCanvas.stroke();

        canvas.close();
        pdfCanvas.release();
    }
}
