package bd.edu.seu.advocaid.controller;
import bd.edu.seu.advocaid.model.Case;
import bd.edu.seu.advocaid.model.User;
import bd.edu.seu.advocaid.repository.AddCaseRepository;
import bd.edu.seu.advocaid.service.UserDashboardService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class UserDashboardController {

    private final UserDashboardService userDashboardService;
    private final AddCaseRepository addCaseRepository;


    public UserDashboardController(UserDashboardService userDashboardService, AddCaseRepository addCaseRepository) {
        this.userDashboardService = userDashboardService;
        this.addCaseRepository = addCaseRepository;
    }

    @GetMapping("/userDashboard")
    public String userDashboard(Model model, Principal principal) {
        if (principal != null) {
            String email = null, name = null, picture = null;

            if (principal instanceof OAuth2AuthenticationToken token) {
                Map<String, Object> attributes = token.getPrincipal().getAttributes();
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                picture = (String) attributes.get("picture");

                userDashboardService.saveOrUpdateOAuthUser(email, name, picture);

            } else if (principal instanceof UsernamePasswordAuthenticationToken authToken) {
                Object principalObj = authToken.getPrincipal();
                if (principalObj instanceof UserDetails userDetails) {
                    email = userDetails.getUsername();
                    User user = userDashboardService.loadManualUser(email);
                    if (user != null) {
                        name = user.getName();
                        picture = user.getPicture();
                        userDashboardService.setOnlineStatus(user);
                    }
                }
            }

            model.addAttribute("email", email);
            model.addAttribute("name", name);
            model.addAttribute("picture", picture != null ? picture : "/images/profile/default-user.png");


            int[] civil = new int[12];
            int[] criminal = new int[12];
            List<Case> userCases = addCaseRepository.findByUserEmail(email);
            for (Case c : userCases) {
                if (c.getFilingDate() != null) {
                    LocalDate date = c.getFilingDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    if (date.getYear() == 2025) {
                        int month = date.getMonthValue() - 1;
                        if ("civil".equalsIgnoreCase(c.getCaseType())) civil[month]++;
                        else if ("criminal".equalsIgnoreCase(c.getCaseType())) criminal[month]++;
                    }
                }
            }
            model.addAttribute("civilCounts", Arrays.stream(civil).boxed().toList());
            model.addAttribute("criminalCounts", Arrays.stream(criminal).boxed().toList());

        }

        return "userDashboard";
    }
}
