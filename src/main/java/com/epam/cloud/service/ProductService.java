package com.epam.cloud.service;

import com.epam.cloud.data.ProductFilterData;
import com.epam.cloud.model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductFilterData productFilterData);
    Product createProduct(Product product);
    void updateProduct(String code, Product product);
    void deleteProduct(String code);
    List<Product> findProductsByCategoryCode(String categoryCode);
    Product getProductByCode(String code);
}
