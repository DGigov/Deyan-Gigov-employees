package bg.sirma.stage2.Assignment;

import org.joda.time.LocalDate;

/**
 * @author Deyan
 *
 */
public class Employer {
    private int empID;
    private int projectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    
    public Employer(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo) {
        super();
        this.empID = empID;
        this.projectID = projectID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "empID=" + empID + ", projectID=" + projectID + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo;
    }

    
    
}
