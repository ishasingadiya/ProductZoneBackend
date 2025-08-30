package com.bitsnbytes.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitsnbytes.product.dto.CategoryDTO;
import com.bitsnbytes.product.repository.CategoryRepository;
import com.bitsnbytes.product.service.CategoryService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// get all categories
	@GetMapping
	public List<CategoryDTO> getAllCategories() {
		return categoryService.getAllCategories();
	}

	// create Categories
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO saveCategory = categoryService.createCategory(categoryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveCategory);
	}

	// get category by id
	@GetMapping("/{id}")
	public CategoryDTO getCategoryById(@PathVariable Long id) {
		return categoryService.getCategoryById(id);
	}

	// delete category
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Long id, CategoryRepository categoryRepository) {
	    try {
	        categoryRepository.deleteById(id);
	        return ResponseEntity.noContent().build();
	    } catch (DataIntegrityViolationException ex) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	            .body("Cannot delete category because it is still referenced by products.");
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body("Error deleting category: " + ex.getMessage());
	    }
	}
}
