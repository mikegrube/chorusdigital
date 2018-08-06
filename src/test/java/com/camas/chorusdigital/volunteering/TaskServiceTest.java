package com.camas.chorusdigital.volunteering;

import com.camas.chorusdigital.UnitTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@Category(UnitTest.class)
public class TaskServiceTest {

	private EventService eventServiceMock;
	private TaskServiceImpl taskService;

	@Before
	public void setUp() {
		eventServiceMock = Mockito.mock(EventServiceImpl.class);
		taskService = new TaskServiceImpl();
		taskService.setEventService(eventServiceMock);
	}

	@Test
	public void whenEventsNeededReturnThem() {

		//Set up mock
		List<Event> events = new ArrayList<>();
		Event event = new Event();
		event.setName("Test Event");
		events.add(event);
		when(eventServiceMock.activeEvents()).thenReturn(events);

		Iterable<Event> actualEvents = taskService.availableEvents();
		int ct = 0;
		for (Event evt : actualEvents) {
			ct++;
		}
		Assert.assertEquals("One event should exist", 1, ct);
	}
}
