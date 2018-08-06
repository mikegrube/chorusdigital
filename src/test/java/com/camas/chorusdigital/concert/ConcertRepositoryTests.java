package com.camas.chorusdigital.concert;

import com.camas.chorusdigital.season.Season;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConcertRepositoryTests {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private ConcertRepository concertRepository;

	private Season season1;
	private Season season2;
	private Concert concert1;
	private Concert concert2;	//Active
	private Concert concert3;
	private Concert concert4;	//Duplicate title, different season

	@Before
	public void setUp() {
		season1 = new Season();
		season1.setStartYear("1980");
		season1 = testEntityManager.persistAndFlush(season1);

		season2 = new Season();
		season2.setStartYear("1981");
		season2 = testEntityManager.persistAndFlush(season2);

		concert1 = testEntityManager.persistAndFlush(makeConcert1(season1));
		concert2 = testEntityManager.persistAndFlush(makeConcert2(season1));
		concert3 = testEntityManager.persistAndFlush(makeConcert3(season2));
		concert4 = testEntityManager.persistAndFlush(makeConcert1(season2));
	}

	@Test
	public void findAllByOrderByTitleAscShouldReturnSortedList() {

		Concert firstReturned = null;
		Concert secondReturned = null;
		Concert thirdReturned = null;
		Concert fourthReturned = null;
		Iterable<Concert> concerts = concertRepository.findAllByOrderByTitleAsc();
		int ct = 0;
		for (Concert concert : concerts) {
			ct++;
			if (ct == 1) {
				firstReturned = concert;
			} else if (ct == 2) {
				secondReturned = concert;
			} else if (ct == 3) {
				thirdReturned = concert;
			} else if (ct == 4) {
				fourthReturned = concert;
			}
		}

		assertThat(ct).isEqualTo(4);
		assertThat(firstReturned.getTitle()).isEqualTo("Beyond the First");
		assertThat(secondReturned.getTitle()).isEqualTo("Fine Concert");
		assertThat(thirdReturned.getTitle()).isEqualTo("Fine Concert");
		assertThat(fourthReturned.getTitle()).isEqualTo("Wonderful Concert");

	}

	@Test
	public void findByActiveShouldReturnActive() {

		Concert firstReturned = null;
		Concert concert = concertRepository.findByActive(true);

		assertThat(concert.getTitle()).isEqualTo("Beyond the First");

	}

	@Test
	public void findBySeasonShouldReturnSeasonConcerts() {

		Iterable<Concert> concerts = concertRepository.findBySeason(season1);

		int ct = 0;
		for (Concert concert : concerts) {
			ct++;

			assertThat(concert.getSeason().getStartYear()).isEqualTo("1980");
		}

		assertThat(ct).isEqualTo(2);
	}

	@Test
	public void findBySeasonAndTitleShouldReturnConcert() {

		assertThat(concert1.getTitle()).isEqualTo("Fine Concert");

		Concert concert = concertRepository.findBySeasonAndTitle(season1, "Fine Concert");
		assertThat(concert.getSeason().getStartYear()).isEqualTo("1980");
		assertThat(concert.getTitle()).isEqualTo("Fine Concert");
	}

	@Test
	public void findAllByTitleOrderByTitleAscShouldReturnAllWithTitle() {

		Iterable<Concert> concerts = concertRepository.findAllByTitleOrderByTitleAsc("Fine Concert");

		int ct = 0;
		for (Concert concert : concerts) {
			ct++;

			assertThat(concert.getTitle()).isEqualTo("Fine Concert");
		}

		assertThat(ct).isEqualTo(2);

	}

	@Test
	public void findAllByTitleContainingIgnoreCaseShouldReturnMatch() {

		Concert firstReturned = null;
		Concert secondReturned = null;
		Concert thirdReturned = null;
		Iterable<Concert> concerts = concertRepository.findAllByTitleContainingIgnoreCase("concert");
		int ct = 0;
		for (Concert concert : concerts) {
			ct++;
			if (ct == 1) {
				firstReturned = concert;
			} else if (ct == 2) {
				secondReturned = concert;
			} else if (ct == 3) {
				thirdReturned = concert;
			}
		}

		assertThat(ct).isEqualTo(3);
		assertThat(firstReturned.getTitle()).contains("Concert");
		assertThat(secondReturned.getTitle()).contains("Concert");
		assertThat(thirdReturned.getTitle()).contains("Concert");

	}

	@Test
	public void findAllByOrderBySeasonStartYearAscTitleAscShouldReturnAllForSeason() {

		Concert firstReturned = null;
		Concert secondReturned = null;
		Concert thirdReturned = null;
		Concert fourthReturned = null;
		Iterable<Concert> concerts = concertRepository.findAllByOrderBySeasonStartYearAscTitleAsc();
		int ct = 0;
		for (Concert concert : concerts) {
			ct++;
			if (ct == 1) {
				firstReturned = concert;
			} else if (ct == 2) {
				secondReturned = concert;
			} else if (ct == 3) {
				thirdReturned = concert;
			} else if (ct == 4) {
				fourthReturned = concert;
			}
		}

		assertThat(ct).isEqualTo(4);
		assertThat(firstReturned.getTitle()).isEqualTo("Beyond the First");
		assertThat(secondReturned.getTitle()).isEqualTo("Fine Concert");
		assertThat(thirdReturned.getTitle()).isEqualTo("Fine Concert");
		assertThat(fourthReturned.getTitle()).isEqualTo("Wonderful Concert");

	}

	//-----

	private Concert makeConcert1(Season season) {

		Concert concert = new Concert();
		concert.setSeason(season);
		concert.setTitle("Fine Concert");
		concert.setPrefix("000");
		concert.setActive(false);

		return concert;
	}

	private Concert makeConcert2(Season season) {

		Concert concert = new Concert();
		concert.setSeason(season);
		concert.setTitle("Beyond the First");
		concert.setPrefix("000");
		concert.setActive(true);

		return concert;
	}

	private Concert makeConcert3(Season season) {

		Concert concert = new Concert();
		concert.setSeason(season);
		concert.setTitle("Wonderful Concert");
		concert.setPrefix("000");
		concert.setActive(false);

		return concert;
	}

}
