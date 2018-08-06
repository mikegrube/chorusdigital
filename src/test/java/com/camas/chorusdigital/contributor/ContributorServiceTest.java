package com.camas.chorusdigital.contributor;

import com.camas.chorusdigital.UnitTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class ContributorServiceTest {

	private ContributorService contributorService;
	private ContributorRepository contributorRepositoryMock;

	@Before
	public void setUp() {
		contributorService = new ContributorServiceImpl();
		contributorRepositoryMock = Mockito.mock(ContributorRepository.class);
		((ContributorServiceImpl) contributorService).setRepository(contributorRepositoryMock);
	}

	@Test
	public void whenBankNeeded_GetWhenExisting() {

		//Setup
		Contributor bank = new Contributor();
		bank.setLastName(Contributor.getBankName());
		when(contributorRepositoryMock.findByLastName("Lastname")).thenReturn(bank);

		Contributor contributor = contributorService.findBank();
		assertNotNull(contributor);
		assertEquals(Contributor.getBankName(), bank.getLastName());
	}

	@Test
	public void whenBankNeeded_GetWhenNotExisting() {

		//Setup
		when(contributorRepositoryMock.findByLastName("Lastname")).thenReturn(null);
		when(contributorRepositoryMock.save(any(Contributor.class))).then(AdditionalAnswers.returnsFirstArg());

		Contributor contributor = contributorService.findBank();
		assertNotNull(contributor);
		assertEquals(Contributor.getBankName(), contributor.getLastName());
	}

	@Test
	public void whenUnknownNeeded_GetWhenExisting() {

		//Setup
		Contributor unknown = new Contributor();
		unknown.setLastName(Contributor.getUnknownName());
		when(contributorRepositoryMock.findByLastName("Lastname")).thenReturn(unknown);

		Contributor contributor = contributorService.findUnknown();
		assertNotNull(contributor);
		assertEquals(Contributor.getUnknownName(), unknown.getLastName());
	}

	@Test
	public void whenUnknownNeeded_GetWhenNotExisting() {

		//Setup
		when(contributorRepositoryMock.findByLastName("Lastname")).thenReturn(null);
		when(contributorRepositoryMock.save(any(Contributor.class))).then(AdditionalAnswers.returnsFirstArg());

		Contributor contributor = contributorService.findUnknown();
		assertNotNull(contributor);
		assertEquals(Contributor.getUnknownName(), contributor.getLastName());
	}
}
