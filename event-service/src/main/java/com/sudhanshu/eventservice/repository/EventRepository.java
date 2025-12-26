package com.sudhanshu.eventservice.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sudhanshu.eventservice.entity.Event;
import com.sudhanshu.eventservice.entity.EventType;

public interface EventRepository extends JpaRepository<Event, UUID> {

	Page<Event> findByUserId(String userId, Pageable pageable);
	
	Page<Event> findByUserIdAndType(String userId, EventType type, Pageable pageable);
}
