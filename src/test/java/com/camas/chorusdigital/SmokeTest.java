package com.camas.chorusdigital;

import static org.assertj.core.api.Assertions.assertThat;

import com.camas.chorusdigital.season.SeasonController;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class SmokeTest {

	@Autowired
	private SeasonController controller;

	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
}