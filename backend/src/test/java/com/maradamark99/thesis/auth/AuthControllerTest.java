package com.maradamark99.thesis.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AuthService authService;

	@Test
	public void testRegistrationHappyPath() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/auth/register")
				.content(objectMapper.writeValueAsString(TestDataProvider.VALID_USER))
				.contentType("application/json")
				.accept("application/json"))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	public void testRegistrationWithInvalidUserShouldReturn400() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/api/v1/auth/register")
				.content(objectMapper.writeValueAsString(TestDataProvider.INVALID_USER))
				.contentType("application/json")
				.accept("application/json"))
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	private static class TestDataProvider {
		private static RegistrationRequest VALID_USER = new RegistrationRequest("testuser@test.com", "testuser",
				"P@ssw0rd");

		private static RegistrationRequest INVALID_USER = new RegistrationRequest("whatever", "a",
				"sad");

		private TestDataProvider() {
		}
	}

}
