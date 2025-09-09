package bd.edu.seu.advocaid.configuration;
import bd.edu.seu.advocaid.model.User;
import bd.edu.seu.advocaid.repository.RegistrationRepository;
import bd.edu.seu.advocaid.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.Optional;

@Configuration
public class SecurityConfiguration {

    private final RegistrationRepository registrationRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfiguration(RegistrationRepository registrationRepository, CustomOAuth2UserService customOAuth2UserService) {
        this.registrationRepository = registrationRepository;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/registration", "/registration/save", "/login", "/logout").permitAll()
                        .requestMatchers("/", "/dashboard").permitAll()
                        .requestMatchers("/userDashboard","/submitCase","/addCase","/caseInfo","/editCase/**","/deleteCase/**","/appointmentCalendar","/saveAppointment",
                                "/updateAppointment",
                                "/deleteAppointment").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/libs/**").permitAll()
                        .requestMatchers("/admin").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .failureUrl("/login?error=true")


                        .successHandler((request, response, authentication) -> {
                            String email = authentication.getName();
                            Optional<User> optionalUser = registrationRepository.findByEmail(email);
                            if (optionalUser.isPresent()) {
                                User user = optionalUser.get();
                                user.setStatus("online");
                                registrationRepository.save(user);
                            }

                            if (email.equalsIgnoreCase("aminur0777@gmail.com")) {
                                response.sendRedirect("/");
                            } else {
                                response.sendRedirect("/userDashboard");
                            }
                        })
                )


                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler((request, response, authentication) -> {
                            String email = null;

                            if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
                                email = (String) oauthUser.getAttribute("email");
                            }

                            if (email != null) {
                                Optional<User> optionalUser = registrationRepository.findByEmail(email);
                                if (optionalUser.isPresent()) {
                                    User user = optionalUser.get();
                                    user.setStatus("online");
                                    registrationRepository.save(user);
                                }
                            }

                            if ("aminur0777@gmail.com".equalsIgnoreCase(email)) {
                                response.sendRedirect("/");
                            } else {
                                response.sendRedirect("/userDashboard");
                            }
                        })
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                )

                .authenticationProvider(authenticationProvider())

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .addLogoutHandler((request, response, authentication) -> {
                            if (authentication != null) {
                                String email = null;
                                if (authentication.getPrincipal() instanceof org.springframework.security.oauth2.core.user.DefaultOAuth2User oauthUser) {
                                    email = oauthUser.getAttribute("email");
                                } else {
                                    email = authentication.getName();
                                }

                                if (email != null) {
                                    Optional<User> optionalUser = registrationRepository.findByEmail(email);
                                    optionalUser.ifPresent(user -> {
                                        user.setStatus("offline");
                                        registrationRepository.save(user);
                                    });
                                }
                            }
                        })
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String email = authentication.getName();
                String password = authentication.getCredentials().toString();

                User user = registrationRepository.findByEmail(email)
                        .orElseThrow(() -> new BadCredentialsException("User not found"));

                if (!passwordEncoder().matches(password, user.getPassword())) {
                    throw new BadCredentialsException("Invalid password");
                }

                return new UsernamePasswordAuthenticationToken(user, password, new ArrayList<>());
            }
        };

        authProvider.setUserDetailsService(username ->
                registrationRepository.findByEmail(username)
                        .map(user -> (UserDetails) user)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"))
        );
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
