package bd.edu.seu.advocaid.service;
import bd.edu.seu.advocaid.interfaces.CaseInfoServiceInterface;
import bd.edu.seu.advocaid.model.Case;
import bd.edu.seu.advocaid.repository.AddCaseRepository;
import bd.edu.seu.advocaid.repository.CaseInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseInfoService implements CaseInfoServiceInterface {

    private final CaseInfoRepository caseInfoRepository;
    private final AddCaseRepository addCaseRepository;


    public CaseInfoService(CaseInfoRepository caseInfoRepository, AddCaseRepository addCaseRepository) {
        this.caseInfoRepository = caseInfoRepository;
        this.addCaseRepository = addCaseRepository;
    }

    @Override
    public Case getCaseById(String id, String userEmail) {
        return caseInfoRepository.findByIdAndUserEmail(id, userEmail).orElse(null);
    }

    @Override
    public List<Case> getCasesByUserEmail(String userEmail) {
        return addCaseRepository.findByUserEmail(userEmail);
    }

    @Override
    public List<Case> getAllCasesByUser(String userEmail) {
        return caseInfoRepository.findByUserEmail(userEmail);
    }

    @Override
    public void deleteCaseById(String id, String userEmail) {
        caseInfoRepository.findByIdAndUserEmail(id, userEmail).ifPresent(caseInfoRepository::delete);
    }

    @Override
    public List<Case> searchAndSortCases(String email, String keyword, String sortBy) {
        if (keyword != null && !keyword.isEmpty()) {
            return caseInfoRepository.findByUserEmailAndCaseNoContainingIgnoreCase(email, keyword);
        } else if ("caseNoAsc".equals(sortBy)) {
            return caseInfoRepository.findByUserEmailOrderByCaseNoAsc(email);
        } else if ("caseNoDesc".equals(sortBy)) {
            return caseInfoRepository.findByUserEmailOrderByCaseNoDesc(email);
        } else {
            return caseInfoRepository.findByUserEmail(email);
        }
    }

}
