package bd.edu.seu.advocaid.controller;
import bd.edu.seu.advocaid.model.User;
import bd.edu.seu.advocaid.repository.RegistrationRepository;
import bd.edu.seu.advocaid.service.RegistrationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
public class DashboardController {

    private final RegistrationService registrationService;
    private final RegistrationRepository registrationRepository;

    public DashboardController(RegistrationService registrationService, RegistrationRepository registrationRepository) {
        this.registrationService = registrationService;
        this.registrationRepository = registrationRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            String email = null, name = null, picture = null;

            if (principal instanceof OAuth2AuthenticationToken token) {
                Map<String, Object> attributes = token.getPrincipal().getAttributes();
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                picture = (String) attributes.get("picture");


                Optional<User> optionalUser = registrationRepository.findByEmail(email);
                if (optionalUser.isEmpty()) {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setPicture(picture);
                    newUser.setStatus("online");
                    registrationRepository.save(newUser);
                } else {
                    User user = optionalUser.get();
                    user.setStatus("online");
                    registrationRepository.save(user);
                }

            } else if (principal instanceof UsernamePasswordAuthenticationToken authToken) {
                Object principalObj = authToken.getPrincipal();
                if (principalObj instanceof UserDetails userDetails) {
                    email = userDetails.getUsername();
                    Optional<User> optionalUser = registrationRepository.findByEmail(email);
                    if (optionalUser.isPresent()) {
                        User u = optionalUser.get();
                        name = u.getName();
                        picture = u.getPicture();
                    }
                }
            }


            model.addAttribute("email", email);
            model.addAttribute("name", name);
            model.addAttribute("picture", picture != null ? picture : "/images/profile/default-user.png");
        }

        return "dashboard";
    }
}
