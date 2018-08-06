package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.UnitTest;
import com.camas.chorusdigital.contributor.ContributorService;
import com.camas.chorusdigital.performance.PerformanceService;
import com.camas.chorusdigital.season.Season;
import com.camas.chorusdigital.season.SeasonService;
import com.camas.chorusdigital.venue.VenueService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Category(UnitTest.class)
public class ConcertServiceTests {

	private ConcertServiceImpl concertService;
	private ConcertRepository concertRepositoryMock;

	@Before
	public void setUp() {
		concertService = new ConcertServiceImpl();
		concertRepositoryMock = Mockito.mock(ConcertRepository.class);
		concertService.setRepository(concertRepositoryMock);
	}

	@Test
	public void whenActivated_OnlyActive() {

		//Set up mock
		List<Concert> concerts = new ArrayList<>();
		Concert concert1 = new Concert();
		concert1.setTitle("Concert 1");
		concert1.setActive(false);
		concerts.add(concert1);
		Concert concert2 = new Concert();
		concert2.setTitle("Concert 2");
		concert1.setActive(true);
		Mockito.when(concertRepositoryMock.findAll()).thenReturn(concerts);

		Mockito.when(concertRepositoryMock.save(concert1)).thenReturn(concert1);
		Mockito.when(concertRepositoryMock.save(concert2)).thenReturn(concert2);

		concertService.makeActive(concert1);

		Assert.assertTrue("Concert 1 should be active", concert1.isActive());
		Assert.assertFalse("Concert 2 should not be active", concert2.isActive());
	}

}
