package com.epam.cloud.facade;

import java.util.List;
import com.epam.cloud.model.Category;
import com.epam.cloud.model.Product;


public interface CategoryFacade
{
	List<Category> getCategories(String sort, String field);
	Category createCategory(Category category);
	void updateCategory(String code, Category category);
	void deleteCategory(String code);
	void assignProductToCategory(List<Product> products, String code);
	void unassignProductToCategory(List<Product> products, String code);
	Category getCategoryByCode(String code);
}
