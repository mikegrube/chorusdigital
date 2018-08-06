package com.camas.chorusdigital.program;

import com.camas.chorusdigital.UnitTest;
import com.camas.chorusdigital.concert.Concert;
import com.camas.chorusdigital.season.Season;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@Category(UnitTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {
		"repository.path=/data/cdrepository/",
})
public class ConcertWorkTrackServiceTest {

	@Value("${repository.path}")
	private String repositoryPath;

	private ConcertWorkTrackServiceImpl concertWorkTrackService;

	@Before
	public void setUp() {
		concertWorkTrackService = new ConcertWorkTrackServiceImpl();
	}

	@Test
	public void whenConcertPath() {
/*
		Season season = new Season();
		season.setStartYear("2010");
		Concert concert = new Concert();
		concert.setTitle("Pretty Music");
		concert.setSeason(season);

		String path = concertWorkTrackService.getConcertPath(concert);
		assertEquals("/data/cdrepository/audio/2010-Pretty Music/", path);
*/	}

	@Test
	public void whenTrackPath() {
/*
		Season season = new Season();
		season.setStartYear("2010");
		Concert concert = new Concert();
		concert.setTitle("Pretty Music");
		concert.setSeason(season);
		Work work = new Work();
		ConcertWork concertWork = new ConcertWork();
		concertWork.setConcert(concert);
		concertWork.setWork(work);
		ConcertWorkTrack concertWorkTrack = new ConcertWorkTrack();
		concertWorkTrack.setConcertWork(concertWork);
		concertWorkTrack.setTrack(1);
		concertWorkTrack.setTitle("First mvt");

		String path = concertWorkTrackService.getTrackAudioPath(concertWorkTrack);
		assertEquals("/data/cdrepository/audio/2010-Pretty Music/1 First mvt.m4a", path);
*/	}

}
