package com.Event.service;



import com.Event.Model.Event;
import com.Event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> searchEvents(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return eventRepository.findAll();
        }
        return eventRepository
                .findByTitleContainingIgnoreCaseOrTypeContainingIgnoreCase(
                        keyword, keyword
                );
    }
}