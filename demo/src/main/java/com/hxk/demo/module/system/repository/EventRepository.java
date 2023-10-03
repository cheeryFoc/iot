package com.hxk.demo.module.system.repository;

import com.hxk.demo.module.system.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
