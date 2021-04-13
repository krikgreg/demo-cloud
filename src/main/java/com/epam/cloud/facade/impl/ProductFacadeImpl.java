package com.epam.cloud.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.cloud.data.ProductFilterData;
import com.epam.cloud.facade.ProductFacade;
import com.epam.cloud.model.Product;
import com.epam.cloud.service.ProductService;

@Component
public class ProductFacadeImpl implements ProductFacade
{

	@Autowired
	private ProductService productService;

	@Override
	public List<Product> getProducts(ProductFilterData productFilterData)
	{
		return productService.getProducts(productFilterData);
	}

	@Override
	public Product createProduct(final Product product)
	{
		return productService.createProduct(product);
	}

	@Override
	public void updateProduct(final String code, final Product product)
	{
		productService.updateProduct(code, product);
	}

	@Override
	public void deleteProduct(final String code)
	{
		productService.deleteProduct(code);
	}

	@Override
	public List<Product> findProductsByCategoryCode(final String code)
	{
		return productService.findProductsByCategoryCode(code);
	}

	@Override
	public Product getProductByCode(final String code)
	{
		return productService.getProductByCode(code);
	}
}
