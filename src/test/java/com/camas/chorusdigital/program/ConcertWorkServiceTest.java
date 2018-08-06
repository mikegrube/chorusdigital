package com.camas.chorusdigital.program;

import com.camas.chorusdigital.UnitTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class ConcertWorkServiceTest {

	private ConcertWorkServiceImpl concertWorkService;
	private WorkService workServiceMock;
	private WorkMusicContributorService workMusicContributorServiceMock;

	@Before
	public void setUp() {
		concertWorkService = new ConcertWorkServiceImpl();
		workServiceMock  = Mockito.mock(WorkServiceImpl.class);
		workMusicContributorServiceMock = Mockito.mock(WorkMusicContributorServiceImpl.class);
		concertWorkService.setWorkService(workServiceMock);
		concertWorkService.setWorkMusicContributorService(workMusicContributorServiceMock);
	}

	@Test
	public void whenNeedAvailableWorks_ComposersExist() {
		Work work1 = new Work();
		work1.setTitle("Work1");
		Work work2 = new Work();
		work2.setTitle("Work2");
		List<Work> works = new ArrayList<>();
		works.add(work1);
		works.add(work2);
		when(workServiceMock.list()).thenReturn(works);

		MusicContributor musicContributor1 = new MusicContributor();
		musicContributor1.setFirstName("Aaron");
		musicContributor1.setLastName("Copland");
		MusicContributor musicContributor2 = new MusicContributor();
		musicContributor2.setFirstName("Andy");
		musicContributor2.setLastName("Haslam");
		WorkMusicContributor workMusicContributor1 = new WorkMusicContributor();
		WorkMusicContributor workMusicContributor2 = new WorkMusicContributor();
		workMusicContributor1.setWork(work1);
		workMusicContributor1.setMusicContributor(musicContributor1);
		workMusicContributor2.setWork(work2);
		workMusicContributor2.setMusicContributor(musicContributor2);
		when(workMusicContributorServiceMock.composerForWork(work1)).thenReturn(workMusicContributor1);
		when(workMusicContributorServiceMock.composerForWork(work2)).thenReturn(workMusicContributor2);

		Iterable<WorkDisplay> workDisplays = concertWorkService.availableWorks();
		Iterator iter = workDisplays.iterator();
		int ct = 0;
		WorkDisplay wd1 = null;
		WorkDisplay wd2 = null;
		while(iter.hasNext()) {
			ct++;
			if (ct == 1) {
				wd1 = (WorkDisplay) iter.next();
			} else if (ct == 2) {
				wd2 = (WorkDisplay) iter.next();
			}
		}
		assertEquals(2, ct);
		assertEquals("Work1 - Copland, Aaron", wd1.getTitle());
		assertEquals("Work2 - Haslam, Andy", wd2.getTitle());

	}

	@Test
	public void whenNeedAvailableWorks_ComposerNotExist() {
		Work work1 = new Work();
		work1.setTitle("Work1");
		Work work2 = new Work();
		work2.setTitle("Work2");
		List<Work> works = new ArrayList<>();
		works.add(work1);
		works.add(work2);
		when(workServiceMock.list()).thenReturn(works);

		MusicContributor musicContributor1 = new MusicContributor();
		musicContributor1.setFirstName("Aaron");
		musicContributor1.setLastName("Copland");
		WorkMusicContributor workMusicContributor1 = new WorkMusicContributor();
		workMusicContributor1.setWork(work1);
		workMusicContributor1.setMusicContributor(musicContributor1);
		when(workMusicContributorServiceMock.composerForWork(work1)).thenReturn(workMusicContributor1);
		when(workMusicContributorServiceMock.composerForWork(work2)).thenReturn(null);

		Iterable<WorkDisplay> workDisplays = concertWorkService.availableWorks();
		Iterator iter = workDisplays.iterator();
		int ct = 0;
		WorkDisplay wd1 = null;
		WorkDisplay wd2 = null;
		while(iter.hasNext()) {
			ct++;
			if (ct == 1) {
				wd1 = (WorkDisplay) iter.next();
			} else if (ct == 2) {
				wd2 = (WorkDisplay) iter.next();
			}
		}
		assertEquals(2, ct);
		assertEquals("Work1 - Copland, Aaron", wd1.getTitle());
		assertEquals("Work2 - missing", wd2.getTitle());

	}

}
