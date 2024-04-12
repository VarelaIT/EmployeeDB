package Logic;

import Entities.Employee;

import java.sql.Date;

public class EmployeeRequest extends Employee implements IEmployeeRequest{

    public EmployeeRequest(String name, String lastName, Date birthDate, Integer dpId) {
        super(name, lastName, birthDate, dpId);
    }

    @Override
    public boolean verifyInput() {
        return false;
    }
}
