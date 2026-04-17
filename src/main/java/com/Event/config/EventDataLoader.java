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
        saveIfNotExists("Conférence IA 2025", "CONFERENCE",
                "Intelligence artificielle et machine learning", "Amphithéâtre A");
        saveIfNotExists("Soirée de bienvenue", "SOIREE",
                "Rencontre étudiants première année", "Hall principal");
        saveIfNotExists("Atelier Spring Boot", "ATELIER",
                "Développement web avec Spring Boot 3", "Salle 204");
        saveIfNotExists("Hackathon 24h", "CONFERENCE",
                "Compétition de programmation toute la nuit", "Campus Tech");
        saveIfNotExists("Workshop React", "ATELIER",
                "Introduction au développement frontend moderne", "Labo Info");
    }

    private void saveIfNotExists(String title, String type, String desc, String location) {
        if (!eventRepository.existsByTitle(title)) {
            eventRepository.save(new Event(null, title, type, desc, location));
        }
    }
    }
