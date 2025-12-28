package com.sudhanshu.eventservice.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.sudhanshu.eventservice.entity.Event;
import com.sudhanshu.eventservice.entity.EventType;

public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {
	//Pagination
	Page<Event> findByUserId(String userId, Pageable pageable);
	
	Page<Event> findByUserIdAndType(String userId, EventType type, Pageable pageable);
	
	//derived top-N
	List<Event> findTop10ByUserIdOrderByTimestampDesc(String userId);
	
	//JPQL aggregation
	@Query("SELECT e.type, count(e) FROM Event e GROUP BY e.type")
	List<Object[]> countEventByType();
	
	@Query(
			value = """
					SELECT type, status, COUNT(*) AS cnt 
					FROM events
					WHERE timestamp BETWEEN :from AND :to 
					GROUP BY type, status,
					""",
					nativeQuery = true
			)
	List<Object[]> countEventsByTypeAndStatusInRange(Instant from, Instant to);
	
}
