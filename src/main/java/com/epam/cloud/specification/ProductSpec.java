package com.epam.cloud.specification;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.criteria.Path;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import com.epam.cloud.model.Product;

public class ProductSpec
{
	public static Specification<Product> contain(String name) {
		return ((root, query, criteriaBuilder) -> {
			if (StringUtils.isNotEmpty(name)) {
				return criteriaBuilder.like(root.get("name"), "%" + name + "%");
			}
			return null;
		});
	}

	public static Specification<Product> betweenPrice(final BigDecimal priceFrom, final BigDecimal priceTo) {
		return ((root, query, criteriaBuilder) -> {
			Path<BigDecimal> path = root.get("price");
			if (Objects.nonNull(priceFrom) && Objects.nonNull(priceTo)) {
				return criteriaBuilder.between(path, priceFrom, priceTo);
			} else if (Objects.nonNull(priceFrom)) {
				return criteriaBuilder.greaterThanOrEqualTo(path, priceFrom);
			} else if (Objects.nonNull(priceTo)) {
				return criteriaBuilder.lessThanOrEqualTo(path, priceTo);
			}
			return null;
		});
	}
}
