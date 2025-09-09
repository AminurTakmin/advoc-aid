package bd.edu.seu.advocaid.service;
import bd.edu.seu.advocaid.interfaces.AddCaseServiceInterface;
import bd.edu.seu.advocaid.model.Case;
import bd.edu.seu.advocaid.repository.AddCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddCaseService implements AddCaseServiceInterface {

    private final AddCaseRepository addCaseRepository;

    public AddCaseService(AddCaseRepository addCaseRepository) {
        this.addCaseRepository = addCaseRepository;
    }

    @Override
    public void saveCase(Case newCase) {
        addCaseRepository.save(newCase);
    }

    @Override
    public Case getCaseById(String id) {
        return addCaseRepository.findById(id).orElse(null);
    }

    @Override
    public List<Case> getCasesByUserEmail(String userEmail) {
        return addCaseRepository.findByUserEmail(userEmail);
    }
}
