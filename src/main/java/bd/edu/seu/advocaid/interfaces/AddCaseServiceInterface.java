package bd.edu.seu.advocaid.interfaces;
import bd.edu.seu.advocaid.model.Case;
import java.util.List;

public interface AddCaseServiceInterface {
    void saveCase(Case newCase);
    Case getCaseById(String id);
    List<Case> getCasesByUserEmail(String userEmail);
}
