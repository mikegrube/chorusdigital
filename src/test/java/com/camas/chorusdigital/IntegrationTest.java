package com.camas.chorusdigital;

import com.camas.chorusdigital.concert.ConcertController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class IntegrationTest {

	@Autowired
	private ConcertController concertController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(concertController).isNotNull();
	}

	@Test
	public void testName() throws Exception {
		//arrange

		//act

		//assert
	}
}
