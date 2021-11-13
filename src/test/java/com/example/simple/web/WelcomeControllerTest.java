package com.example.simple.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class WelcomeControllerTest {

	@Autowired
	private WebApplicationContext context;
	private MockMvc MockMvc;

	@Before
	public void setUp() throws Exception {
		MockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testWelcome() throws Exception {
		MockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(model().attribute("course", containsString("DevOps")));
	}

}
