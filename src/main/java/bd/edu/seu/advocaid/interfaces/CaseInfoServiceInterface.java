package bd.edu.seu.advocaid.interfaces;
import bd.edu.seu.advocaid.model.Case;
import java.util.List;

public interface CaseInfoServiceInterface {
    Case getCaseById(String id, String userEmail);
    List<Case> getCasesByUserEmail(String userEmail);
    List<Case> getAllCasesByUser(String userEmail);
    void deleteCaseById(String id, String userEmail);
    List<Case> searchAndSortCases(String email, String keyword, String sortBy);

}
