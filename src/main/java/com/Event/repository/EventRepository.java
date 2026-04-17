package com.Event.repository;

import com.Event.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    // Recherche par titre OU type (insensible à la casse)
    List<Event> findByTitleContainingIgnoreCaseOrTypeContainingIgnoreCase(
            String title, String type
    );
}