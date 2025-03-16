package com.mystore.app.service;

import com.mystore.app.entity.Product;
import com.mystore.app.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private Integer currentId = 1;

	@Autowired
	private ProductRepository productRepository;

	public Product addProduct(Product product) {
		product.setId(currentId++);
		productRepository.save(product);
		return product;
	}

	public Page<Product> getAllProducts(int page, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		Pageable pageable = PageRequest.of(page, pageSize, sort);
		return productRepository.findAll(pageable);
	}

	public Product getProduct(Integer id) {
		Optional<Product> productOptional = productRepository.findById(id);
		return productOptional.get();
	}

	public Product updateProduct(Integer id, Product product) {
		Product p = productRepository.findById(id).get();
		if (p == null)
			return null;
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		p.setCategory(product.getCategory());
		p.setStockQuantity(product.getStockQuantity());
		productRepository.save(p);
		return p;
	}

	public String deleteProduct(Integer id) {
		Product p = productRepository.findById(id).get();
		if (p == null)
			return "Product Not Found";
		productRepository.delete(p);
		return "Product Deleted Successfully";
	}

	public List<Product> searchProductsByName(String name) {
		return productRepository.findByNameIgnoreCaseContaining(name);
	}

	public List<Product> searchProductsByCategory(String category) {
		return productRepository.findByCategoryIgnoreCase(category);
	}

	public List<Product> filterProductsByPriceBetween(Double minPrice, Double maxPrice) {
		return productRepository.findByPriceBetween(minPrice, maxPrice);
	}

	public List<Product> filterProductsByStockRange(Integer minStock, Integer maxStock) {
		return productRepository.findByStockQuantityBetween(minStock, maxStock);
	}
}