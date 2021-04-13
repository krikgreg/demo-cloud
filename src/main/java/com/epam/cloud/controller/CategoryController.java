package com.epam.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.epam.cloud.facade.CategoryFacade;
import com.epam.cloud.model.Category;
import com.epam.cloud.model.Product;


@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController
{
	@Autowired
	private CategoryFacade categoryFacade;

	@GetMapping
	public List<Category> getAllCategories(@RequestParam(required = false) String sort, @RequestParam(required = false) String field) {
		return categoryFacade.getCategories(sort, field);
	}

	@GetMapping(value = "/{code}")
	public Category getCategoryByCode(@PathVariable(value = "code") String code) {
		return categoryFacade.getCategoryByCode(code);
	}

	@PostMapping
	public ResponseEntity<String> createCategory(@RequestBody Category category){
		return ResponseEntity.status(HttpStatus.CREATED).body(categoryFacade.createCategory(category).getCode());
	}

	@PutMapping(value = "/{code}")
	@ResponseStatus(HttpStatus.OK)
	public void updateCategory(@PathVariable(value = "code") String code, @RequestBody Category category) {
		categoryFacade.updateCategory(code, category);
	}

	@DeleteMapping(value = "/{code}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCategory(@PathVariable(value = "code") String code) {
		categoryFacade.deleteCategory(code);
	}

	@PutMapping(value = "/{code}/assign")
	public void assignProductToCategory(@RequestBody List<Product> products, @PathVariable(value = "code") String code) {
		categoryFacade.assignProductToCategory(products, code);
	}

	@DeleteMapping(value = "/{code}/unassign")
	public void unassignProductToCategory(@RequestBody List<Product> products, @PathVariable(value = "code") String code) {
		categoryFacade.unassignProductToCategory(products, code);
	}

}
