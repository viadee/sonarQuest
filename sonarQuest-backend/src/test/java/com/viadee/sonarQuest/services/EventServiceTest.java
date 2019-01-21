package com.viadee.sonarquest.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.entities.Event;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventServiceTest {


	@Autowired
    private EventService eventService;
    
	
	@Test
	public void testServiceEvent() {
		
		
		Event event1 = new Event();
		event1.setTimestamp(new Timestamp(System.currentTimeMillis()));
		event1.setTitle("event1");
		eventService.storeEvent(event1);
		
		
		// get Event lists
		List<Event> events1 = eventService.getLatestEvent();


        // verify result
        assertTrue(events1.get(0).equals(event1));
        
        
        
		Event event2 = new Event();
		event2.setTimestamp(new Timestamp(System.currentTimeMillis()));
		event2.setTitle("event2");
		eventService.storeEvent(event2);
		
		Event event3 = new Event();
		event3.setTimestamp(new Timestamp(System.currentTimeMillis()));
		event3.setTitle("event3");
		eventService.storeEvent(event3);
        


		// get Event lists
		List<Event> events2 = eventService.get2LatestEvents();
		List<Event> events3 = eventService.get3LatestEvents();
		


		
        // verify result
        assertTrue (events2.contains(event3));
        assertTrue (events2.contains(event2));
        assertFalse(events2.contains(event1));
        
        // verify result
        assertTrue (events3.contains(event3));
        assertTrue (events3.contains(event2));
        assertTrue (events3.contains(event1));
		
	}
}

