package Logic;

import Entities.Employee;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EmployeeRequest implements IEmployeeRequest{

    protected String name;
    protected String lastName;
    protected java.sql.Date birthDate;
    protected java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());
    protected Integer departmentId;

    public EmployeeRequest(String name, String lastName, String birthDate, Integer dpId){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        this.name = name;
        this.lastName = lastName;
        try {
            this.birthDate = new java.sql.Date(formatter.parse(birthDate).getTime());
        } catch (ParseException e) {
            throw new RuntimeException("Invalid format while trying to store employee.");
        }
        this.departmentId = dpId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public Integer getDepartmentId() {
        return departmentId;
    }

    @Override
    public boolean verifyInput() {
        if (name == null || lastName == null || birthDate == null || departmentId == null)
            return false;

        if (name.length() < 2 || lastName.length() < 2 || today.getYear() - birthDate.getYear() < 18 )
            return false;

        return true;
    }
}
