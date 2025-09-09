package bd.edu.seu.advocaid.repository;
import bd.edu.seu.advocaid.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserDashboardRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}
