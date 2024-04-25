package Logic;

import Entities.EmployeesPerDepartment;
import Persistence.IReportEmployeesPerDepartment;
import Persistence.ReportEmployeesPerDepartment;

public class ReportEmployeesPerDepartmentResponse extends EmployeesPerDepartment implements IReportEmployeesPerDepartmentResponse {

    ReportEmployeesPerDepartmentResponse(IReportEmployeesPerDepartment report){
        this.id = report.getId();
        this.department = report.getDepartment();
        this.total= report.getTotal();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public int getTotal() {
        return total;
    }
}
