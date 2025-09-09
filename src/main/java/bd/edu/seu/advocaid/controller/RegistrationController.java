package bd.edu.seu.advocaid.controller;
import bd.edu.seu.advocaid.model.User;
import bd.edu.seu.advocaid.service.RegistrationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @GetMapping("/registration")
    public String registrationPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "registration";
    }


    @PostMapping("/registration/save")
    public String registerUser(@ModelAttribute User u, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        u.setRoles(new ArrayList<>());
        u.getRoles().add("ROLE_USER");
        registrationService.saveUser(u);
        return "redirect:/login";
    }

}
