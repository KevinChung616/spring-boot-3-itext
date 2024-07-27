package com.jtsp.springboot3itextpdf.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
public class InboundTransaction {
    private Long id;
    private String receiverName;
    private String driverName;
    private LocalDateTime createdAt;
    private List<Product> productList;
}
