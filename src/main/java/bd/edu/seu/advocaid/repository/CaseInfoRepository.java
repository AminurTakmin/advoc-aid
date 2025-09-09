package bd.edu.seu.advocaid.repository;
import bd.edu.seu.advocaid.model.Case;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CaseInfoRepository extends MongoRepository<Case, String> {
    List<Case> findByUserEmail(String userEmail);
    Optional<Case> findByIdAndUserEmail(String id, String userEmail);
    List<Case> findByUserEmailAndCaseNoContainingIgnoreCase(String userEmail, String keyword);
    List<Case> findByUserEmailOrderByCaseNoAsc(String userEmail);
    List<Case> findByUserEmailOrderByCaseNoDesc(String userEmail);

}
