package com.epam.cloud.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cloud.exception.BusinessException;
import com.epam.cloud.facade.ProductFacade;
import com.epam.cloud.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
public class ProductControllerTest extends AbstractControllerTest
{
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductFacade productFacade;

	@Test
	public void shouldCreateNewProduct() throws Exception
	{
		final Product product = getRandomInstance().nextObject(Product.class);

		mockMvc.perform(post("/products").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(getJsonAsString(product))).andExpect(status().isCreated());

		assertThat(productFacade.getProductByCode(product.getCode())).isNotNull();
	}

	@Test
	public void shouldUpdateExistingProduct() throws Exception
	{
		final String name = "test";
		final Product product = getRandomInstance().nextObject(Product.class);
		productFacade.createProduct(product);
		product.setName(name);

		mockMvc.perform(put("/products/" + product.getCode()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(getJsonAsString(product))).andExpect(status().isOk());

		assertThat(productFacade.getProductByCode((product.getCode())).getName()).isEqualTo(name);
	}

	@Test
	public void shouldReturnProductByCode() throws Exception
	{
		final Product product = getRandomInstance().nextObject(Product.class);
		final String code = product.getCode();
		productFacade.createProduct(product);

		mockMvc.perform(get("/products/" + product.getCode()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(getJsonAsString(product))).andExpect(status().isOk());

		assertThat(productFacade.getProductByCode((product.getCode())).getCode()).isEqualTo(code);
	}

	@Test()
	public void shouldDeleteExistingCategory() throws Exception
	{
		final Product prod = getRandomInstance().nextObject(Product.class);
		productFacade.createProduct(prod);

		final String code = prod.getCode();
		mockMvc.perform(delete("/products/" + code)).andExpect(status().isOk());

		Exception exception = assertThrows(BusinessException.class, () -> {
			productFacade.getProductByCode(code);
		});
		final String actualMessage = exception.getMessage();
		final String expectedMessage = "There is no product with code " + code;
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void shouldFindAllProductsByPriceRange() throws Exception
	{
		final BigDecimal priceFrom = BigDecimal.ONE;
		final BigDecimal priceTo = BigDecimal.valueOf(4.);
		applyRandomPrice(getRandomProductList());

		final MockHttpServletResponse response = mockMvc.perform(get("/products")
				.param("priceFrom", priceFrom.toString())
				.param("priceTo", priceTo.toString())).andExpect(status().isOk()).andReturn().getResponse();

		final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>()
		{
		});
		assertThat(result).map(Product::getPrice).allMatch(
				price -> price.intValue() >= priceFrom.intValue() && price.intValue() <= priceTo.intValue());
	}

	@Test
	public void shouldSortProductsByNameAsc() throws Exception
	{
		getRandomProductList().forEach(product -> productFacade.createProduct(product));

		MockHttpServletResponse response = mockMvc.perform(get("/products").param("sort", "asc")
				.param("field", "name")).andExpect(
				status().isOk()).andReturn().getResponse();

		final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>()
		{
		});
		assertThat(result).map(Product::getName).isSortedAccordingTo(Comparator.naturalOrder());
	}

	@Test
	public void shouldSortProductsByPriceDesc() throws Exception
	{
		applyRandomPrice(getRandomProductList());

		MockHttpServletResponse response = mockMvc.perform(get("/products").param("sort", "desc")
				.param("field", "price"))
				.andExpect(status().isOk()).andReturn().getResponse();

		final List<Product> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>()
		{
		});
		assertThat(result).map(Product::getPrice).isSortedAccordingTo(Comparator.reverseOrder());
	}

	private List<Product> getRandomProductList()
	{
		return getRandomInstance().objects(Product.class, 3).collect(Collectors.toList());
	}

	private void applyRandomPrice(final List<Product> products)
	{
		products.forEach(product -> {
			product.setPrice(BigDecimal.valueOf(new Random().nextInt(5)));
			productFacade.createProduct(product);
		});
	}

}
