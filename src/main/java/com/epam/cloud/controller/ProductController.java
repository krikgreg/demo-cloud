package com.epam.cloud.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cloud.data.ProductFilterData;
import com.epam.cloud.facade.ProductFacade;
import com.epam.cloud.model.Product;


@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController
{
	@Autowired
	private ProductFacade productFacade;

	@GetMapping
	public List<Product> getAllProducts(@RequestParam(required = false) final BigDecimal priceFrom,
			@RequestParam(required = false) final BigDecimal priceTo,
			@RequestParam(required = false) final String name,
			@RequestParam(required = false) final String sort,
			@RequestParam(required = false) final String field) {

		return productFacade.getProducts(new ProductFilterData(name, field, priceFrom, priceTo, sort));
	}

	@GetMapping(value = "/{code}")
	public Product getProductById(@PathVariable(value = "code") String code){
		return productFacade.getProductByCode(code);
	}

	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		return ResponseEntity.status(HttpStatus.CREATED).body(productFacade.createProduct(product)) ;
	}

	@PutMapping(value = "/{code}")
	@ResponseStatus(HttpStatus.OK)
	public void updateProduct(@PathVariable(value = "code") String code, @RequestBody Product product){
		productFacade.updateProduct(code, product);
	}

	@DeleteMapping(value = "/{code}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteProduct(@PathVariable(value = "code") String code) {
		productFacade.deleteProduct(code);
	}

	@GetMapping(value = "/{code}/productsByCategory")
	public List<Product> getProductsByCategoryId(@PathVariable(value = "code") String code) {
		return productFacade.findProductsByCategoryCode(code);
	}

}
