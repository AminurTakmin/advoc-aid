package bd.edu.seu.advocaid.service;
import bd.edu.seu.advocaid.interfaces.UserServiceInterface;
import bd.edu.seu.advocaid.model.User;
import bd.edu.seu.advocaid.repository.UserDashboardRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDashboardService implements UserServiceInterface.UserDashboardInterface {

    private final UserDashboardRepository userDashboardRepository;

    public UserDashboardService(UserDashboardRepository userDashboardRepository) {
        this.userDashboardRepository = userDashboardRepository;
    }

    @Override
    public void saveOrUpdateOAuthUser(String email, String name, String picture) {
        Optional<User> optionalUser = userDashboardRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPicture(picture);
            newUser.setStatus("online");
            userDashboardRepository.save(newUser);
        } else {
            User user = optionalUser.get();
            user.setStatus("online");
            userDashboardRepository.save(user);
        }
    }

    @Override
    public User loadManualUser(String email) {
        return userDashboardRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void setOnlineStatus(User user) {
        user.setStatus("online");
        userDashboardRepository.save(user);
    }


    public void injectUserDetailsToModel(Principal principal, Model model) {
        if (principal != null) {
            String email = null, name = null, picture = null;

            if (principal instanceof OAuth2AuthenticationToken token) {
                Map<String, Object> attributes = token.getPrincipal().getAttributes();
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                picture = (String) attributes.get("picture");
                this.saveOrUpdateOAuthUser(email, name, picture);
            } else if (principal instanceof UsernamePasswordAuthenticationToken authToken) {
                Object principalObj = authToken.getPrincipal();
                if (principalObj instanceof UserDetails userDetails) {
                    email = userDetails.getUsername();
                    User user = this.loadManualUser(email);
                    if (user != null) {
                        name = user.getName();
                        picture = user.getPicture();
                        this.setOnlineStatus(user);
                    }
                }
            }

            model.addAttribute("email", email);
            model.addAttribute("name", name);
            model.addAttribute("picture", picture != null ? picture : "/images/profile/default-user.png");
        }
    }

}
