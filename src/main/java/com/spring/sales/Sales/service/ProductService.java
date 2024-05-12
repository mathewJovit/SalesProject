package com.spring.sales.Sales.service;

import com.spring.sales.Sales.dto.ProductDTO;
import com.spring.sales.Sales.entity.Product;
import com.spring.sales.Sales.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    public List<ProductDTO> getAllProducts(Integer pageNo,Integer pageSize) throws ProductNotFoundException;
    public ProductDTO getProductById(Long id) throws ProductNotFoundException;
    public Long addProduct(ProductDTO product) throws ProductNotFoundException;
    public void updateProduct(Long id,Product product) throws ProductNotFoundException;
    public void deleteProduct(Long id) throws ProductNotFoundException;

    public double getTotalRevenue();
    public double getTotalRevenueById(Long id) throws ProductNotFoundException;
}
