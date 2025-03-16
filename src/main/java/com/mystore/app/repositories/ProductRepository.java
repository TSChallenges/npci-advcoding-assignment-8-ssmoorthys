package com.mystore.app.repositories;

import com.mystore.app.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	Page<Product> findAll(Pageable pageable);

	List<Product> findByNameIgnoreCaseContaining(String name);

	List<Product> findByCategoryIgnoreCase(String category);

	List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

	List<Product> findByStockQuantityBetween(Integer minStock, Integer maxStock);

}