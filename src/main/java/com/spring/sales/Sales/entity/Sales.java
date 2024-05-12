package com.spring.sales.Sales.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "Sale")
public class Sales {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productId", nullable = false)
    private Long productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "saleDate", nullable = false)
    private Date saleDate;

    // Constructors
    public Sales() {
    }

    public Sales(Long productId, int quantity, Date saleDate) {
        this.productId = productId;
        this.quantity = quantity;
        this.saleDate = saleDate;
    }

}
