package com.jtsp.springboot3itextpdf.controller;

import com.jtsp.springboot3itextpdf.service.InboundReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final InboundReportService service;

    @Autowired
    public ReportController(InboundReportService service) {
        this.service = service;
    }

    @GetMapping("/inbound-transaction")
    public ResponseEntity<byte[]> generatePDF() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);
        httpHeaders.setContentDispositionFormData("filename", "inbound-transaction.pdf");
        return new ResponseEntity<>(service.generateReport(), httpHeaders, HttpStatus.OK);
    }
}
