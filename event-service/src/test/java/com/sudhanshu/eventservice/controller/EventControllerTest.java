package com.sudhanshu.eventservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudhanshu.eventservice.dto.EventCreateRequest;
import com.sudhanshu.eventservice.dto.EventResponse;
import com.sudhanshu.eventservice.dto.EventUpdateRequest;
import com.sudhanshu.eventservice.entity.EventStatus;
import com.sudhanshu.eventservice.entity.EventType;
import com.sudhanshu.eventservice.exception.NotFoundException;
import com.sudhanshu.eventservice.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;

    @Test
    void createEvent_shouldReturn201AndBody() throws Exception {
        EventCreateRequest request = new EventCreateRequest();
        request.setUserId("user-101");
        request.setType(EventType.VIEW_START);
        request.setMetadata("{\"device\":\"mobile\"}");
        request.setTimestamp(Instant.now());
        EventResponse response = new EventResponse();
        response.setId(UUID.randomUUID());
        response.setUserId(request.getUserId());
        response.setType(request.getType());
        response.setTimestamp(Instant.now());
        response.setStatus(EventStatus.RECEIVED);
        response.setMetadata(request.getMetadata());

        when(eventService.createEvent(any(EventCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is("user-101")))
                .andExpect(jsonPath("$.type", is("VIEW_START")));
    }

    @Test
    void createEvent_shouldFailValidationForMissingUserId() throws Exception {
        EventCreateRequest request = new EventCreateRequest();
        request.setType(EventType.AD_IMPRESSION);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEvent_shouldReturn200() throws Exception {
      UUID id = UUID.randomUUID();

        EventResponse response = new EventResponse();
        response.setId(id);
        response.setUserId("user-101");
        response.setType(EventType.AD_IMPRESSION);
        response.setTimestamp(Instant.now());
        response.setStatus(EventStatus.RECEIVED);

        when(eventService.getEvent(id)).thenReturn(response);

        mockMvc.perform(get("/events/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.userId", is("user-101")));
    }

    @Test
    void getEvent_shouldReturn404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(eventService.getEvent(id)).thenThrow(new NotFoundException("Event not found"));

        mockMvc.perform(get("/events/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void listEvents_shouldReturnPagedList() throws Exception {
        EventResponse r1 = new EventResponse();
        r1.setId(UUID.randomUUID());
        r1.setUserId("user-101");
        r1.setType(EventType.AD_IMPRESSION);
        r1.setStatus(EventStatus.RECEIVED);
        System.out.println("**********\n \n \n *************  "+new PageImpl<>(List.of(r1)));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("timestamp").descending());
        when(eventService.searchEvents("user-101", EventType.AD_IMPRESSION, pageable))
                .thenReturn(new PageImpl<>(List.of(r1)));
//Page<EventResponse>
        mockMvc.perform(get("/events")
                        .param("userId", "user-101")
                        .param("type", "AD_IMPRESSION")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());    }

    @Test
    void updateEvent_shouldReturn200() throws Exception {
        UUID id = UUID.randomUUID();
        EventUpdateRequest request = new EventUpdateRequest();
        request.setMetadata("{\"device\":\"mobile\",\"titleId\":\"MOV123\",\"processedBy\":\"job-7\"}");
        request.setStatus(EventStatus.PROCESSED);
        EventResponse response = new EventResponse();
        response.setId(id);
        response.setUserId("user-101");
        response.setType(EventType.VIEW_START);
        response.setStatus(EventStatus.PROCESSED);

        when(eventService.updateEvent(any(UUID.class), any(EventUpdateRequest.class))).thenReturn(response);

        mockMvc.perform(put("/events/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("PROCESSED")));
    }

    @Test
    void deleteEvent_shouldReturn204() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/events/{id}", id))
                .andExpect(status().is2xxSuccessful());
    }
}