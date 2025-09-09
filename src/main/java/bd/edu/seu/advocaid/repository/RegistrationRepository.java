package bd.edu.seu.advocaid.repository;
import bd.edu.seu.advocaid.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends MongoRepository<User, String> {
    List<User> findAllByName(String n);
    Optional<User> findByEmail(String n);
    Optional<User> findByEmailAndPassword(String e, String p);

}
