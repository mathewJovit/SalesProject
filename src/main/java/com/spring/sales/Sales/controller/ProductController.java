package com.spring.sales.Sales.controller;

import com.spring.sales.Sales.dto.ProductDTO;
import com.spring.sales.Sales.exception.ProductNotFoundException;
import com.spring.sales.Sales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private Environment environment;

    @GetMapping(value = "/products/page/{pageNo}/pagesSize/{pageSize}")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable Integer pageNo, @PathVariable Integer pageSize) throws ProductNotFoundException {
        List<ProductDTO> productList = productService.getAllProducts(pageNo,pageSize);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping(value = "/products/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) throws ProductNotFoundException {
        ProductDTO product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
