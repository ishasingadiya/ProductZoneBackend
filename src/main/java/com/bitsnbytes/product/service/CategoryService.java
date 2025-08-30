package com.bitsnbytes.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitsnbytes.product.dto.CategoryDTO;
import com.bitsnbytes.product.entity.Category;
import com.bitsnbytes.product.exception.CategoryAlreadyExistsException;
import com.bitsnbytes.product.mapper.CategoryMapper;
import com.bitsnbytes.product.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	// create category
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Optional<Category> optionalCategory=categoryRepository.findByName(categoryDTO.getName());
		if(optionalCategory.isPresent()) {
			throw new CategoryAlreadyExistsException("Category "+categoryDTO.getName()+" already exist.");
		}
		Category category = CategoryMapper.toCategoryEntity(categoryDTO);
		category = categoryRepository.save(category);
		return CategoryMapper.toCategortDTO(category);
	}

	// get all category
	@Transactional
	public List<CategoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream().map(CategoryMapper::toCategortDTO).toList();
	}
	// get all category by id
	public CategoryDTO getCategoryById(Long id) {
		Category category=categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Category not found."));
		return CategoryMapper.toCategortDTO(category);
	}
	// delete category
	public String deleteCategory(Long id) {
		categoryRepository.deleteById(id);
		return "Category Successfully Deleted...!";
	}
}
