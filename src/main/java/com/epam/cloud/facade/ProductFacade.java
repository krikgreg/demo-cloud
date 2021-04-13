package com.epam.cloud.facade;

import java.util.List;
import com.epam.cloud.data.ProductFilterData;
import com.epam.cloud.model.Product;


public interface ProductFacade
{
	List<Product> getProducts(ProductFilterData productFilterData);
	Product createProduct(Product product);
	void updateProduct(String code, Product product);
	void deleteProduct(String code);
	List<Product> findProductsByCategoryCode(String categoryCode);
	Product getProductByCode(String code);
}
