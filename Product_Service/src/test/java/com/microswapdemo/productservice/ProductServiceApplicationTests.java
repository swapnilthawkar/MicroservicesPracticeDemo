package com.microswapdemo.productservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microswapdemo.productservice.dto.ProductRequest;
import com.microswapdemo.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
	private ProductRepository productRepository;

    @Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
    @DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);

	}

	@Test
	void shouldCreateProductTest() throws Exception {

		ProductRequest productRequest = grtProductRequest();
		String productReqString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product").
				contentType(MediaType.APPLICATION_JSON).
				content(productReqString)).
				andExpect(status().isCreated());

		Assertions.assertEquals(1, productRepository.findAll().size());


	}

	private ProductRequest grtProductRequest() {
		return ProductRequest.builder().
				name("Apple").
				discription("Iphone14").
				price(BigDecimal.valueOf(1200)).
				build();
	}

}
