package com.jtsp.springboot3itextpdf.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Product {
    private Long id;
    private Long quantity;
    private String name;
    private String supplier;
}
