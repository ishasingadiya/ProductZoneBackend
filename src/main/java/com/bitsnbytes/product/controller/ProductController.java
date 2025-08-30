package com.bitsnbytes.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsnbytes.product.dto.ProductDTO;
import com.bitsnbytes.product.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

	@Autowired
	private ProductService productService;

	// get all Product
	@GetMapping
	public List<ProductDTO> getAllProduct(){
		return productService.getAllProducts();
	}

	// get product by id
	@GetMapping("/{id}")
	public ProductDTO getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}

	// createProduct
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	@PostMapping
	public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
		ProductDTO createProduct = productService.createProduct(productDTO);
		return new ResponseEntity<ProductDTO>(createProduct, HttpStatus.CREATED);
	}

	// update product
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	@PutMapping("/{id}")
	public ProductDTO updateProduct(@PathVariable Long id,@RequestBody ProductDTO productDTO) {
		return productService.updateProduct(id, productDTO);
	}

	// delete product
	@PreAuthorize("hasAuthority('ROLE_SELLER')")
	@DeleteMapping("/{id}")
	public String deleteProduct(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}
}
