package bd.edu.seu.advocaid.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "cases")
public class Case {

    @Id
    private String id;
    private String district;
    private List<String> caseCategories;
    private String caseType;
    private String courtName;
    private String caseNo;
    private String caseYear;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date filingDate;
    private String clientName;
    private String clientPhone;
    private String assignTo;
    private String referenceNo;
    private String fileNo;
    private String opponentName;
    private String opponentPhone;
    private String opponentAdvocate;
    private List<String> opponentWitness;
    private String policeStation;
    private String firNo;
    private String investigatingOfficer;
    private String officerPhone;
    private String userEmail;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public List<String> getCaseCategories() { return caseCategories; }
    public void setCaseCategories(List<String> caseCategories) { this.caseCategories = caseCategories; }

    public String getCaseType() { return caseType; }
    public void setCaseType(String caseType) { this.caseType = caseType; }

    public String getCourtName() { return courtName; }
    public void setCourtName(String courtName) { this.courtName = courtName; }

    public String getCaseNo() { return caseNo; }
    public void setCaseNo(String caseNo) { this.caseNo = caseNo; }

    public String getCaseYear() { return caseYear; }
    public void setCaseYear(String caseYear) { this.caseYear = caseYear; }

    public Date getFilingDate() { return filingDate; }
    public void setFilingDate(Date filingDate) { this.filingDate = filingDate; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getAssignTo() { return assignTo; }
    public void setAssignTo(String assignTo) { this.assignTo = assignTo; }

    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

    public String getFileNo() { return fileNo; }
    public void setFileNo(String fileNo) { this.fileNo = fileNo; }

    public String getOpponentName() { return opponentName; }
    public void setOpponentName(String opponentName) { this.opponentName = opponentName; }

    public String getOpponentPhone() { return opponentPhone; }
    public void setOpponentPhone(String opponentPhone) { this.opponentPhone = opponentPhone; }

    public String getOpponentAdvocate() { return opponentAdvocate; }
    public void setOpponentAdvocate(String opponentAdvocate) { this.opponentAdvocate = opponentAdvocate; }

    public List<String> getOpponentWitness() { return opponentWitness; }
    public void setOpponentWitness(List<String> opponentWitness) { this.opponentWitness = opponentWitness; }

    public String getPoliceStation() { return policeStation; }
    public void setPoliceStation(String policeStation) { this.policeStation = policeStation; }

    public String getFirNo() { return firNo; }
    public void setFirNo(String firNo) { this.firNo = firNo; }

    public String getInvestigatingOfficer() { return investigatingOfficer; }
    public void setInvestigatingOfficer(String investigatingOfficer) { this.investigatingOfficer = investigatingOfficer; }

    public String getOfficerPhone() { return officerPhone; }
    public void setOfficerPhone(String officerPhone) { this.officerPhone = officerPhone; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
}
