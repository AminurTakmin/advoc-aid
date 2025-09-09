package bd.edu.seu.advocaid.service;
import bd.edu.seu.advocaid.model.User;
import bd.edu.seu.advocaid.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    private final RegistrationRepository registrationRepository;

    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public User saveUser(User user){
        return registrationRepository.save(user);
    }


    public List<User> findAll() {
        return registrationRepository.findAll();
    }

    public User findByEmail(String email) {
        return registrationRepository.findByEmail(email).orElse(null);
    }


}
