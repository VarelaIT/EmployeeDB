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
    protected Integer departmentId;

    public EmployeeRequest(String name, String lastName, String birthDate, Integer dpId) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        this.name = name;
        this.lastName = lastName;
        this.birthDate = new java.sql.Date(formatter.parse(birthDate).getTime());
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
}
