package com.Event.Controller;



import com.Event.repository.UserRepository;
import com.Event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserRepository userRepository;

    @GetMapping("/search")
    public String searchPage(
            @RequestParam(required = false) String keyword,
            Authentication authentication,
            Model model) {

        // Récupérer le nom de l'utilisateur connecté
        if (authentication != null) {
            String email = authentication.getName();
            userRepository.findByEmail(email).ifPresent(user ->
                model.addAttribute("userName", user.getName())
            );
        }

        model.addAttribute("events", eventService.searchEvents(keyword));
        model.addAttribute("keyword", keyword);
        return "search";  // → templates/search.html
    }
}