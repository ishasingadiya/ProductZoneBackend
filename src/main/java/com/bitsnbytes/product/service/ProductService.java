package com.bitsnbytes.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsnbytes.product.dto.ProductDTO;
import com.bitsnbytes.product.entity.Category;
import com.bitsnbytes.product.entity.Product;
import com.bitsnbytes.product.exception.CategoryNotFoundException;
import com.bitsnbytes.product.mapper.ProductMapper;
import com.bitsnbytes.product.repository.CategoryRepository;
import com.bitsnbytes.product.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public ProductDTO createProduct(ProductDTO productDTO) {
		/**
		 * id, name, description, price, categoryId this will be save by
		 * payload(@RequestBody)
		 **/
		Category category = categoryRepository.findById(productDTO.getCategoryId())
				.orElseThrow(() -> new CategoryNotFoundException("Category id "+productDTO.getCategoryId()+" not forund"));
		// DTO to entity
		Product product = ProductMapper.toProductEntity(productDTO, category);
		product = productRepository.save(product);

		// entity to dto
		return ProductMapper.toProductDTO(product);
	}

	// get all product
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream().map(ProductMapper::toProductDTO).toList();
	}

	// get by id
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found."));
		return ProductMapper.toProductDTO(product);
	}
	
	//update product
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found."));
		Category category= categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found."));
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());
		product.setCategory(category);
		productRepository.save(product);
		return ProductMapper.toProductDTO(product);
	}
	
	//delete product
	public String deleteProduct(Long id) {
		productRepository.deleteById(id);
		return "Product " +id+" has been deleted!";
	}
}
