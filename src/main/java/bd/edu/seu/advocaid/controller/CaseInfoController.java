package bd.edu.seu.advocaid.controller;
import bd.edu.seu.advocaid.model.Case;
import bd.edu.seu.advocaid.service.CaseInfoService;
import bd.edu.seu.advocaid.service.UserDashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CaseInfoController {

    private final CaseInfoService caseInfoService;
    private final UserDashboardService userDashboardService;

    public CaseInfoController(CaseInfoService caseInfoService, UserDashboardService userDashboardService) {
        this.caseInfoService = caseInfoService;
        this.userDashboardService = userDashboardService;
    }




    @GetMapping("/deleteCase/{id}")
    public String deleteCase(@PathVariable String id, Principal principal) {

        String email = null;
        if (principal instanceof OAuth2AuthenticationToken token) {
            email = (String) token.getPrincipal().getAttributes().get("email");
        } else if (principal instanceof UsernamePasswordAuthenticationToken authToken) {
            Object principalObj = authToken.getPrincipal();
            if (principalObj instanceof UserDetails userDetails) {
                email = userDetails.getUsername();
            }
        }

        caseInfoService.deleteCaseById(id, email);
        return "redirect:/caseInfo";
    }



    @GetMapping("/caseInfo")
    public String getUserCases(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortBy,
            HttpSession session, Model model, Principal principal) {

        userDashboardService.injectUserDetailsToModel(principal, model);
        String email = (String) model.getAttribute("email");
        List<Case> cases = caseInfoService.searchAndSortCases(email, keyword, sortBy);

        model.addAttribute("cases", cases);
        return "caseInfo";
    }



}
