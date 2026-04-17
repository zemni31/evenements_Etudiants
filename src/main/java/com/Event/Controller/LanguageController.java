package com.Event.Controller;



import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import java.util.Locale;

@Controller
public class LanguageController {

    @GetMapping("/change-lang")
    public String changeLang(
            @RequestParam String lang,
            HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response) {

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (localeResolver != null) {
            localeResolver.setLocale(request, response, Locale.forLanguageTag(lang));
        }
        // Retourne à la page précédente
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/search");
    }
}