package com.epam.cloud.service.imp;

import com.epam.cloud.exception.BusinessException;
import com.epam.cloud.model.Category;
import com.epam.cloud.model.Product;
import com.epam.cloud.repository.CategoryRepository;
import com.epam.cloud.repository.ProductRepository;
import com.epam.cloud.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService
{
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Category> getCategories(String sort, String field)
	{
		if (sort != null && field != null)
		{
			return categoryRepository.findAll(Sort.by(sort, field));
		}
		return categoryRepository.findAll();
	}

	@Override
	public Category createCategory(Category category)
	{
		return categoryRepository.save(category);
	}

	@Override
	public void updateCategory(String code, Category category)
	{
		categoryRepository.findByCode(code).ifPresent(uptCategory -> {
			uptCategory.setName(category.getName());
			uptCategory.setDescription(category.getDescription());
			uptCategory.setProducts(category.getProducts());
			categoryRepository.save(uptCategory);
		});
	}

	@Override
	public void deleteCategory(String code)
	{
		categoryRepository.findByCode(code).ifPresent(categoryRepository::delete);
	}

	@Override
	public void assignProductToCategory(final List<Product> products, final String code)
	{
		if (CollectionUtils.isEmpty(products) && StringUtils.isEmpty(code))
		{
			throw new BusinessException("Products or Code is null or empty");
		}
		categoryRepository.findByCode(code)
				.map(category -> {
					final Category category1 = attachProductsToCategory(products, category);
					return categoryRepository.save(category1);
				})
				.orElseThrow(() -> new BusinessException("There is not such category in the system!"));
	}

	@Override
	public void unassignProductToCategory(final List<Product> products, final String code)
	{
		if (CollectionUtils.isEmpty(products) && StringUtils.isEmpty(code))
		{
			throw new BusinessException("Products or Code is null or empty");
		}
		 categoryRepository.findByCode(code)
				.map(category -> categoryRepository.save(removeProductsFromCategory(products, category)))
				.orElseThrow(() -> new BusinessException("There is not such category in the system!"));
	}

	@Override
	public Category getCategoryById(final String code)
	{
		return categoryRepository.findByCode(code)
				.orElseThrow(() -> new BusinessException("There is no category with code " + code));
	}

	private Category attachProductsToCategory(List<Product> products, Category category)
	{
		if (!CollectionUtils.isEmpty(products))
		{
			final List<String> productCods = products.stream().filter(Objects::nonNull).map(Product::getCode).collect(
					Collectors.toList());
			final List<Product> productsFromDb = productCods.stream().map(productCode -> productRepository.findByCode(productCode))
					.flatMap(Optional::stream).collect(Collectors.toList());
			Set<Product> newSet = new HashSet<>(category.getProducts());
			newSet.addAll(productsFromDb);
			category.setProducts(newSet);
		}
		return category;
	}

	private Category removeProductsFromCategory(List<Product> products, final Category category)
	{
		Set<Product> newSet = new HashSet<>(category.getProducts());
		newSet.removeAll(products);
		category.setProducts(newSet);
		return category;
	}

}
