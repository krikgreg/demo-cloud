package com.epam.cloud.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.cloud.facade.CategoryFacade;
import com.epam.cloud.model.Category;
import com.epam.cloud.model.Product;
import com.epam.cloud.service.CategoryService;

@Component
public class CategoryFacadeImpl implements CategoryFacade
{
	@Autowired
	private CategoryService categoryService;

	@Override
	public List<Category> getCategories(final String sort, final String field)
	{
		return categoryService.getCategories(sort, field);
	}

	@Override
	public Category createCategory(final Category category)
	{
		return categoryService.createCategory(category);
	}

	@Override
	public void updateCategory(final String code, final Category category)
	{
		categoryService.updateCategory(code, category);
	}

	@Override
	public void deleteCategory(final String code)
	{
		categoryService.deleteCategory(code);
	}

	@Override
	public void assignProductToCategory(final List<Product> products, final String code)
	{
		categoryService.assignProductToCategory(products, code);
	}

	@Override
	public void unassignProductToCategory(final List<Product> products, final String code)
	{
		categoryService.unassignProductToCategory(products, code);
	}

	@Override
	public Category getCategoryByCode(final String code)
	{
		return categoryService.getCategoryById(code);
	}
}
