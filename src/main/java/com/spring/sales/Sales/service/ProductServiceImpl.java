package com.spring.sales.Sales.service;

import com.spring.sales.Sales.dto.ProductDTO;
import com.spring.sales.Sales.dto.SalesDTO;
import com.spring.sales.Sales.entity.Product;
import com.spring.sales.Sales.entity.Sales;
import com.spring.sales.Sales.exception.ProductNotFoundException;
import com.spring.sales.Sales.repository.ProductRepository;
import com.spring.sales.Sales.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service(value = "productService")
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    Environment environment;

    @Override
    public List<ProductDTO> getAllProducts(Integer pageNo,Integer pageSize) throws ProductNotFoundException {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> page = productRepository.findAll(pageable);
        if (page.isEmpty()) {
            throw new ProductNotFoundException(environment.getProperty("Product.PRODUCT_NOT_FOUND"));
        }
        List<Product> products = page.getContent();
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO productsDto = new ProductDTO();
            productsDto.setId(product.getId());
            productsDto.setName(product.getName());
            productsDto.setDescription(product.getDescription());
            productsDto.setPrice(product.getPrice());
            productsDto.setQuantity(product.getQuantity());
            List<Sales> sales = product.getSales();
            List<SalesDTO> salesDTOS = new LinkedList<>();

            if (!sales.isEmpty()) {
                salesDTOS = sales.stream()
                        .map(s->new SalesDTO(s.getId(),s.getProductId(),s.getQuantity(),s.getSaleDate()))
                        .toList();
            }
            productsDto.setSales(salesDTOS);
            productDTOs.add(productsDto);
        });

        return productDTOs;

    }

    @Override
    public ProductDTO getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> optional = productRepository.findById(id);
        Product product = optional.orElseThrow(()->new ProductNotFoundException(environment.getProperty("Product.PRODUCT_NOT_FOUND")));
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setPrice(product.getPrice());
        List<Sales> sales = product.getSales();
        List<SalesDTO> salesDTOS = new LinkedList<>();

        if (!sales.isEmpty()) {
            salesDTOS = sales.stream()
                    .map(s->new SalesDTO(s.getId(),s.getProductId(),s.getQuantity(),s.getSaleDate()))
                    .toList();
        }
        productDTO.setSales(salesDTOS);
        return productDTO;
    }

    @Override
    public Long addProduct(ProductDTO product) throws ProductNotFoundException {
        Product productEntity = new Product();
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setQuantity(product.getQuantity());
        productEntity.setPrice(product.getPrice());
        List<SalesDTO> salesDTOS = product.getSales();
        List<Sales> sales= new LinkedList<>();
        if (!salesDTOS.isEmpty()) {
            sales = salesDTOS.stream()
                    .map(s->new Sales(s.getProductId(),s.getQuantity(),s.getSaleDate()))
                    .toList();
        }
        productEntity.setSales(sales);
        Product productEntity2 = productRepository.save(productEntity);
        return productEntity2.getId();
    }

    @Override
    public void updateProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product existingProduct = optionalProduct.orElseThrow(() -> new ProductNotFoundException("Product.PRODUCT_NOT_FOUND"));
        // Update the existing product with the new data
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        // Save the updated product
        productRepository.save(existingProduct);

    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optional = productRepository.findById(id);
        optional.orElseThrow(() -> new ProductNotFoundException("Product.PRODUCT_NOT_FOUND"));
        productRepository.deleteById(id);
    }

    @Override
    public double getTotalRevenue() {
        Iterable<Product> products = productRepository.findAll();
        AtomicReference<Double> price= new AtomicReference<>((double) 0);
        AtomicReference<Double> totalRevenue = new AtomicReference<>((double) 0);
        products.forEach(product -> {
            price.set(product.getPrice());
            List<Sales> sales = product.getSales();
            double sum = 0;
            if (!sales.isEmpty()) {
                sum = sales.stream()
                        .map(s -> s.getQuantity() * price.get())
                        .mapToDouble(Double::doubleValue)
                        .sum();
            }
            totalRevenue.set(totalRevenue.get() + sum);
        });
        return totalRevenue.get();
    }

    @Override
    public double getTotalRevenueById(Long id) throws ProductNotFoundException {
        Optional<Product> optional = productRepository.findById(id);
        Product product = optional.orElseThrow(()->new ProductNotFoundException(environment.getProperty("Product.PRODUCT_NOT_FOUND")));
        double price = product.getPrice();
        List<Sales> sales = product.getSales();
        double sum = 0;
        if (!sales.isEmpty()) {
            sum = sales.stream()
                    .map(s -> s.getQuantity() * price)
                    .mapToDouble(Double::doubleValue)
                    .sum();
        }
        return sum;
    }
}
