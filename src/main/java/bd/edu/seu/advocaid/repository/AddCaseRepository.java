package bd.edu.seu.advocaid.repository;
import bd.edu.seu.advocaid.model.Case;
import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AddCaseRepository extends MongoRepository<Case, String> {
    @NonNull
    List<Case> findByUserEmail(@NonNull String userEmail);
    @NonNull
    Optional<Case> findById(@NonNull String id);
}
