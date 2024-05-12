package com.spring.sales.Sales.repository;

import com.spring.sales.Sales.entity.Sales;
import org.springframework.data.repository.CrudRepository;

public interface SalesRepository extends CrudRepository<Sales,Long> {
}
