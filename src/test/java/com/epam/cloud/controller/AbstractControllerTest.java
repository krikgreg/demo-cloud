package com.epam.cloud.controller;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application-test.properties")
public class AbstractControllerTest {

	public static EasyRandom getRandomInstance() {
		EasyRandomParameters easyRandomParameters = new EasyRandomParameters()
				.seed(300)
				.stringLengthRange(3, 10)
				.excludeField(FieldPredicates.named("id"));
		return new EasyRandom(easyRandomParameters);
	}

	public static String getJsonAsString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
