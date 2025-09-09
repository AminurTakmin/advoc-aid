package bd.edu.seu.advocaid.controller;

import bd.edu.seu.advocaid.model.Case;
import bd.edu.seu.advocaid.service.AddCaseService;
import bd.edu.seu.advocaid.service.UserDashboardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class AddCaseController {

    private final AddCaseService addCaseService;
    private final UserDashboardService userDashboardService;

    public AddCaseController(AddCaseService addCaseService, UserDashboardService userDashboardService) {
        this.addCaseService = addCaseService;
        this.userDashboardService = userDashboardService;
    }

    @GetMapping("/addCase")
    public String showAddCaseForm(Model model, HttpSession session, Principal principal) {
        model.addAttribute("caseObj", new Case());


        userDashboardService.injectUserDetailsToModel(principal, model);
        return "addCase";
    }


    @PostMapping("/submitCase")
    public String saveCase(@ModelAttribute("caseObj") Case caseObj, Principal principal) {


        if (caseObj.getId() != null && caseObj.getId().trim().isEmpty()) {
            caseObj.setId(null);
        }


        if (principal instanceof OAuth2AuthenticationToken token) {
            String email = (String) token.getPrincipal().getAttributes().get("email");
            caseObj.setUserEmail(email);
        } else if (principal instanceof UsernamePasswordAuthenticationToken authToken) {
            Object principalObj = authToken.getPrincipal();
            if (principalObj instanceof UserDetails userDetails) {
                caseObj.setUserEmail(userDetails.getUsername());
            }
        }

        addCaseService.saveCase(caseObj);
        return "redirect:/caseInfo";
    }


    @GetMapping("/editCase/{id}")
    public String editCase(@PathVariable String id, Model model, Principal principal) {
        userDashboardService.injectUserDetailsToModel(principal, model);
        Case caseObj = addCaseService.getCaseById(id);
        if (caseObj == null) return "redirect:/caseInfo";

        String userEmail = (String) model.getAttribute("email");
        if (!caseObj.getUserEmail().equals(userEmail)) return "redirect:/caseInfo";

        model.addAttribute("caseObj", caseObj);
        model.addAttribute("categoryList", caseObj.getCaseCategories());
        model.addAttribute("witnessList", caseObj.getOpponentWitness());
        return "addCase";
    }



}
