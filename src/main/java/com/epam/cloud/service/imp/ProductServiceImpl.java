package com.epam.cloud.service.imp;

import com.epam.cloud.data.ProductFilterData;
import com.epam.cloud.exception.BusinessException;
import com.epam.cloud.model.Category;
import com.epam.cloud.model.Product;
import com.epam.cloud.repository.CategoryRepository;
import com.epam.cloud.repository.ProductRepository;
import com.epam.cloud.service.ProductService;
import com.epam.cloud.specification.ProductSpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class ProductServiceImpl implements ProductService
{
	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Product> getProducts(ProductFilterData productFilterData)
	{
		Sort sort = getSort(productFilterData);
		Specification<Product> productSpecification = createSpecification(productFilterData);
	return productRepository.findAll(productSpecification, sort);
	}

	private Sort getSort(final ProductFilterData productFilterData)
	{
		if (StringUtils.isNotEmpty(productFilterData.getSort()) &&
				StringUtils.isNotEmpty(productFilterData.getField()))

		{
			return Sort.by(Sort.Direction.fromString(productFilterData.getSort()), productFilterData.getField());
		}
		final String defaultProperty = "name";
		return Sort.by(Sort.Direction.ASC, defaultProperty);
	}

	@Override
	public Product createProduct(Product product)
	{
		return productRepository.save(product);
	}

	@Override
	public void updateProduct(String code, Product product)
	{
		productRepository.findByCode(code).ifPresent(updProduct -> {
			updProduct.setName(product.getName());
			updProduct.setPrice(product.getPrice());
			updProduct.setDescription(product.getDescription());
			productRepository.save(updProduct);
		});
	}

	@Override
	public void deleteProduct(String code)
	{
		productRepository.findByCode(code).ifPresent(productRepository::delete);
	}

	@Override
	public List<Product> findProductsByCategoryCode(String code)
	{
		return categoryRepository.findByCode(code)
		.map(Category::getProducts)
		.map(ArrayList::new)
		.orElseThrow(() -> new BusinessException("There is no such category with code " + code));
	}

	@Override
	public Product getProductByCode(String code)
	{
		return productRepository.findByCode(code).orElseThrow(() -> new BusinessException("There is no product with code " + code));
	}

	private Specification<Product> createSpecification(final ProductFilterData productFilterData) {
		return ProductSpec.contain(productFilterData.getName())
				.and(ProductSpec.betweenPrice(productFilterData.getPriceFrom(), productFilterData.getPriceTo()));
	}
}
