package com.sudhanshu.eventservice.repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.sudhanshu.eventservice.entity.Event;
import com.sudhanshu.eventservice.entity.EventStatus;
import com.sudhanshu.eventservice.entity.EventType;

import jakarta.persistence.criteria.Predicate;

public class EventSpecifications {

	private EventSpecifications() {}
	
	public static Specification<Event> filter(
			String userId,
			EventType type,
			EventStatus status,
			Instant from,
			Instant to
			){
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(userId != null && !userId.isBlank()) {
				predicates.add(cb.equal(root.get("userId"), userId));
			}
			if(type != null) {
				predicates.add(cb.equal(root.get("type"), type));
			}
			if(type != null) {
				predicates.add(cb.equal(root.get("status"), status));
			}
			if(from != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), from));
			}
			if(to != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), to));
			}

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	
}
