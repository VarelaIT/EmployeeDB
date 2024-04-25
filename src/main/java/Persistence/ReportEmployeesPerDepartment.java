package Persistence;

import Entities.EmployeesPerDepartment;

public class ReportEmployeesPerDepartment extends EmployeesPerDepartment implements IReportEmployeesPerDepartment{

    ReportEmployeesPerDepartment(int id, String department, int total){
        this.id = id;
        this.department = department;
        this.total = total;
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
