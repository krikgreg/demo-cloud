package com.epam.cloud.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cloud.exception.BusinessException;
import com.epam.cloud.facade.CategoryFacade;
import com.epam.cloud.facade.ProductFacade;
import com.epam.cloud.model.Category;
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
public class CategoryControllerTest extends AbstractControllerTest
{

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CategoryFacade categoryFacade;

	@Autowired
	private ProductFacade productFacade;

	@Test
	public void shouldCreateNewCategory() throws Exception {
		final Category category = getRandomInstance().nextObject(Category.class);

		mockMvc.perform(post("/categories")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJsonAsString(category)))
				.andExpect(status().isCreated());

		assertThat(categoryFacade.getCategoryByCode(category.getCode())).isNotNull();
	}

	@Test
	public void shouldUpdateExistingCategory() throws Exception {
		final String name = "test";
		final Category category = getRandomInstance().nextObject(Category.class);
		categoryFacade.createCategory(category);
		category.setName(name);

		mockMvc.perform(put("/categories/" + category.getCode())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJsonAsString(category)))
				.andExpect(status().isOk());

		assertThat(categoryFacade.getCategoryByCode(category.getCode()).getName()).isEqualTo(name);
	}

	@Test
	public void shouldReturnCategoryByCode() throws Exception {
		final Category category = getRandomInstance().nextObject(Category.class);
		final String code = category.getCode();
		categoryFacade.createCategory(category);

		mockMvc.perform(get("/categories/" + category.getCode())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJsonAsString(category)))
				.andExpect(status().isOk());

		assertThat(categoryFacade.getCategoryByCode(category.getCode()).getCode()).isEqualTo(code);
	}

	@Test()
	public void shouldDeleteExistingCategory() throws Exception {
		final Category category = getRandomInstance().nextObject(Category.class);
		categoryFacade.createCategory(category);

		final String code = category.getCode();
		mockMvc.perform(delete("/categories/" + code))
				.andExpect(status().isOk());

		Exception exception = assertThrows(BusinessException.class, () -> {
			categoryFacade.getCategoryByCode(code);
		});
		final String actualMessage = exception.getMessage();
		final String expectedMessage = "There is no category with code " + code;
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void shouldAssignProductsToExistingCategory() throws Exception {
		final Category category = getRandomInstance().nextObject(Category.class);
		categoryFacade.createCategory(category);
		final Product product = getRandomInstance().nextObject(Product.class);
		productFacade.createProduct(product);

		mockMvc.perform(put("/categories/" + category.getCode() + "/assign")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJsonAsString(List.of(product))))
				.andExpect(status().isOk());

		final List<String> actual = categoryFacade.getCategoryByCode(category.getCode()).getProducts().stream().map(
				Product::getCode).collect(Collectors.toList());
		final String expected = product.getCode();
		assertThat(actual).containsAll(List.of(expected));
	}

	@Test
	public void shouldUnAssignProductsToExistingCategory() throws Exception {
		final Category category = getRandomInstance().nextObject(Category.class);
		categoryFacade.createCategory(category);
		final Product product = getRandomInstance().nextObject(Product.class);
		final List<Product> products = Arrays.asList(product);
		category.setProducts(Set.copyOf(products));

		mockMvc.perform(delete("/categories/" + category.getCode() + "/unassign")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(getJsonAsString(products)))
				.andExpect(status().isOk());

		assertThat(categoryFacade.getCategoryByCode(category.getCode()).getProducts()).isEmpty();
	}

	@Test
	public void shouldFindAllCategories() throws Exception {
		final List<Category> categories = getRandomInstance().objects(Category.class, 3).collect(Collectors.toList());
		categories.forEach(category -> categoryFacade.createCategory(category));

		final MockHttpServletResponse response = mockMvc.perform(get("/categories"))
				.andExpect(status().isOk())
				.andReturn().getResponse();

		final List<Category> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
		});
		assertThat(result).map(Category::getCode)
				.containsAll(categories.stream().map(Category::getCode).collect(Collectors.toList()));
	}

	@Test
	public void shouldSortByCategoryNameAsc() throws Exception {
		final List<Category> categories = getRandomInstance().objects(Category.class, 3).collect(Collectors.toList());

		final MockHttpServletResponse response = mockMvc.perform(get("/categories")
				.param("asc", "name"))
				.andExpect(status().isOk())
				.andReturn().getResponse();

		final List<Category> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
		});
		assertThat(result).map(Category::getName).isSortedAccordingTo(Comparator.naturalOrder());
	}

	@Test
	public void shouldSortByCategoryNameDesc() throws Exception {
		final List<Category> categories = getRandomInstance().objects(Category.class, 3).collect(Collectors.toList());

		final MockHttpServletResponse response = mockMvc.perform(get("/categories")
				.param("desc", "name"))
				.andExpect(status().isOk())
				.andReturn().getResponse();

		final List<Category> result = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<>() {
		});
		assertThat(result).map(Category::getName).isSortedAccordingTo(Comparator.reverseOrder());
	}

}
