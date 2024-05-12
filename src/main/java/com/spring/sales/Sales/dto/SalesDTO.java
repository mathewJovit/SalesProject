package com.spring.sales.Sales.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SalesDTO {
    // Getters and Setters
    private Long id;
    private Long productId;
    private int quantity;
    private Date saleDate;

    // Constructors
    public SalesDTO() {
    }

    public SalesDTO(Long id, Long productId, int quantity, Date saleDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

}

