package com.spring.sales.Sales.exception;

import java.io.Serial;

public class ProductNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    public ProductNotFoundException(String message) {
        super(message);
    }
}
