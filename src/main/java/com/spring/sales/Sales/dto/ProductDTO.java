package com.spring.sales.Sales.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductDTO {
    // Getters and Setters
    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private List<SalesDTO> sales;

    public ProductDTO(){}
    public ProductDTO(Long id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

}