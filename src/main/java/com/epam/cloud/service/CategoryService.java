package com.epam.cloud.service;

import com.epam.cloud.model.Category;
import com.epam.cloud.model.Product;

import java.util.List;



public interface CategoryService {
    List<Category> getCategories(String sort, String field);

    Category createCategory(Category category);

    void updateCategory(String code, Category category);

    void deleteCategory(String code);

    void assignProductToCategory(List<Product> products, String code);

    void unassignProductToCategory(List<Product> products, String code);

    Category getCategoryById(String code);
}
