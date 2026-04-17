package com.Event.config;


import com.Event.Model.Event;
import com.Event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventDataLoader implements CommandLineRunner {

    private final EventRepository eventRepository;

    @Override
    public void run(String... args) {
        eventRepository.save(new Event(null, "Conférence IA 2025",
                "CONFERENCE", "Intelligence artificielle et machine learning", "Amphithéâtre A"));
        eventRepository.save(new Event(null, "Soirée de bienvenue",
                "SOIREE", "Rencontre étudiants première année", "Hall principal"));
        eventRepository.save(new Event(null, "Atelier Spring Boot",
                "ATELIER", "Développement web avec Spring Boot 3", "Salle 204"));
        eventRepository.save(new Event(null, "Hackathon 24h",
                "CONFERENCE", "Compétition de programmation toute la nuit", "Campus Tech"));
        eventRepository.save(new Event(null, "Workshop React",
                "ATELIER", "Introduction au développement frontend moderne", "Labo Info"));
    }
}