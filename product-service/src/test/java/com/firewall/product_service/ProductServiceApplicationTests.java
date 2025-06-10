package com.firewall.product_service;

import com.firewall.product_service.dto.ProductDto;
import com.firewall.product_service.repository.ProductRepo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepo productRepo;

	@Container
	static MySQLContainer mysqlContainer = new MySQLContainer<>("mysql:8.0")
			.withDatabaseName("product-service")
			.withUsername("root")
			.withPassword("12345");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
		registry.add("spring.datasource.username", mysqlContainer::getUsername);
		registry.add("spring.datasource.password", mysqlContainer::getPassword);
	}

	@Test
	void shouldCreateProduct() throws Exception {

	    ProductDto productDto =	getProductRequest();
		String product = objectMapper.writeValueAsString(productDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(String.valueOf(MediaType.APPLICATION_JSON))
				.content(product))
				.andExpect(status().isCreated());
		Assert.assertEquals(1, productRepo.findAll().size());
	}

	private ProductDto getProductRequest() {
		return ProductDto.builder()
				.name("iphone")
				.description("apple")
				.price(BigDecimal.valueOf(1000))
		        .build();
	}

}
