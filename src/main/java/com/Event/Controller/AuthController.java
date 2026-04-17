package com.Event.Controller;



import com.Event.Model.User;

import com.Event.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller

@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // ── Page de connexion ──────────────────────────────────────────
    @GetMapping("/login")           // reste comme ça
    public String loginPage() {
        return "login";
    }

    // ── Formulaire d'inscription (GET) ─────────────────────────────
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // → templates/register.html
    }

    // ── Traitement de l'inscription (POST) ────────────────────────
    @PostMapping("/register")
    public String registerSubmit(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {

        // S'il y a des erreurs de validation (@Email, @NotBlank…)
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.register(user);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException e) {
            // Email déjà utilisé
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

}