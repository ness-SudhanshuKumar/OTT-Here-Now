package com.sudhanshu.eventservice.service;

import com.sudhanshu.eventservice.dto.EventCreateRequest;
import com.sudhanshu.eventservice.dto.EventResponse;
import com.sudhanshu.eventservice.entity.Event;
import com.sudhanshu.eventservice.entity.EventStatus;
import com.sudhanshu.eventservice.entity.EventType;
import com.sudhanshu.eventservice.exception.NotFoundException;
import com.sudhanshu.eventservice.repository.EventRepository;
import com.sudhanshu.eventservice.service.impl.EventServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
public class EventServiceImplTest {

    EventService eventService;
    EventRepository eventRepository;
    @BeforeEach
    void setUp(){
        eventRepository = mock(EventRepository.class);
        eventService = new EventServiceImpl(eventRepository);
    }
    @Test
    void createEvent_shouldPersistAndReturnResponse(){
        EventCreateRequest request = new EventCreateRequest();
        request.setUserId("user-103");
        request.setType(EventType.VIEW_START);
        request.setMetadata("{\"device\":\"mobile\"}");

        Event saved = new Event();
        saved.setId(UUID.randomUUID());
        saved.setUserId(request.getUserId());
        saved.setType(request.getType());
        saved.setTimestamp(Instant.now());
        saved.setStatus(EventStatus.RECEIVED);
        saved.setMetadata(request.getMetadata());

        when(eventRepository.save(any(Event.class))).thenReturn(saved);
        EventResponse response = eventService.createEvent(request);
        System.out.println(saved.getId());
        System.out.println(saved.getUserId());

        assertThat(response.getId()).isEqualTo(saved.getId());
        assertThat(response.getUserId()).isEqualTo("user-103");
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void getEvent_shouldReturnWhenExists(){
        UUID id = UUID.randomUUID();
        Event event = new Event();
        event.setId(id);
        event.setUserId("user-101");
        event.setType(EventType.VIEW_START);

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        EventResponse response = eventService.getEvent(id);

        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getUserId()).isEqualTo("user-101");

    }
    @Test
    void getEvent_shouldThrowWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(eventRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> eventService.getEvent(id));
    }

}
