package bd.edu.seu.advocaid.controller;
import bd.edu.seu.advocaid.model.Appointment;
import bd.edu.seu.advocaid.service.AppointmentService;
import bd.edu.seu.advocaid.service.UserDashboardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final UserDashboardService userDashboardService;

    public AppointmentController(AppointmentService appointmentService, UserDashboardService userDashboardService) {
        this.appointmentService = appointmentService;
        this.userDashboardService = userDashboardService;
    }

    @GetMapping("/appointmentCalendar")
    public String showCalendar(Model model, Principal principal) {
        userDashboardService.injectUserDetailsToModel(principal, model); // inject name/email/pic

        String email = (String) model.getAttribute("email");
        List<Appointment> appointments = appointmentService.getAppointmentsByUser(email);

        model.addAttribute("appointments", appointments);
        return "appointmentCalendar";
    }

    @PostMapping("/saveAppointment")
    public String saveEvent(HttpServletRequest request, Principal principal) {
        String title = request.getParameter("title");
        String date = request.getParameter("date");

        String email = null;
        if (principal instanceof OAuth2AuthenticationToken token) {
            Map<String, Object> attributes = token.getPrincipal().getAttributes();
            email = (String) attributes.get("email");
        } else if (principal instanceof UsernamePasswordAuthenticationToken authToken) {
            Object obj = authToken.getPrincipal();
            if (obj instanceof UserDetails userDetails) {
                email = userDetails.getUsername();
            }
        }

        if (email != null && title != null && date != null) {
            Appointment newEvent = new Appointment(title, date, email);
            appointmentService.save(newEvent);
        }

        return "redirect:/appointmentCalendar";
    }
}

