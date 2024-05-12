package com.spring.sales.Sales.controller;

import com.spring.sales.Sales.dto.ProductDTO;
import com.spring.sales.Sales.entity.Product;
import com.spring.sales.Sales.exception.ProductNotFoundException;
import com.spring.sales.Sales.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(value = "/products")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO product) throws ProductNotFoundException {
        Long productId = productService.addProduct(product);
        String successMessage = environment.getProperty("API.INSERT_SUCCESS") + productId;
        return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/{productId}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long productId, @RequestBody Product product)
            throws ProductNotFoundException {
        productService.updateProduct(productId, product);
        String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
        String successMessage = environment.getProperty("API.DELETE_SUCCESS");
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }
    @GetMapping(value = "/products/totalRevenue")
    public ResponseEntity<Double> getTotalRevenue() throws ProductNotFoundException {
        Double totalRevenue = productService.getTotalRevenue();
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }

    @GetMapping(value = "/products/totalRevenue/{productId}")
    public ResponseEntity<Double> getTotalRevenueById(@PathVariable Long productId) throws ProductNotFoundException {
        Double totalRevenue = productService.getTotalRevenueById(productId);
        return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
    }

}
