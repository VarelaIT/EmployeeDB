package PersistenceTests;

import Entities.IEmployee;
import Entities.Employee;
import Entities.IPersistedEmployee;
import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeePersistenceTest {

    private final IEmployeeRepository employeeRepository;
    private DateFormat bdObj= new SimpleDateFormat("dd-MM-yyyy");

    EmployeePersistenceTest(){
        Connection conn = new DBConn().getConn();
        TableSchemas.dropEmployeesTable(conn);
        TableSchemas.createEmployeesTable(conn);
        conn= null;
        employeeRepository = new EmployeeRepository();
    }

    @Test
    public void persistEmployee() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1987").getTime());
        Integer departmentID = null;
        IEmployee employee = new Employee("Ismael", "Varela", birthDate, departmentID);

        IPersistedEmployee persistedEmployee = employeeRepository.save(employee);

        assertEquals(employee.getName(), persistedEmployee.getName());
        assertEquals(employee.getLastName(), persistedEmployee.getLastName());
        assertEquals(birthDate.toString(), persistedEmployee.getBirthDate().toString());
        assertEquals(departmentID, persistedEmployee.getDepartmentId()); // database returns 0 instead of null
    }

    @Test
    public void getPersistedEmployee() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1993").getTime());
        IEmployee employee = new Employee("Juan Elias", "Rodriguez", birthDate, null);
        IPersistedEmployee newEmployee = employeeRepository.save(employee);

        IPersistedEmployee sameEmployee = employeeRepository.get(newEmployee.getId());

        assertEquals(newEmployee.getId(), sameEmployee.getId());
        assertEquals(newEmployee.getName(), sameEmployee.getName());
        assertEquals(newEmployee.getBirthDate(), sameEmployee.getBirthDate());
    }
    @Test
    public void getPersistedEmployeesList() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1999").getTime());
        IEmployee employeeA = new Employee("Juan", "Rodriguez", birthDate, null);
        IEmployee employeeB = new Employee("Elias", "Mejia", birthDate, null);
        IPersistedEmployee newEmployeeA = employeeRepository.save(employeeA);
        IPersistedEmployee newEmployeeB = employeeRepository.save(employeeB);

        List<IPersistedEmployee> listOfEmployees = employeeRepository.get();

        assertTrue(listOfEmployees.size()>1);
    }

    @Test
    public void  updatePersistedEmployee() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1987").getTime());
        IEmployee employee = new Employee("Lary", "Figurereo", birthDate, null);
        IPersistedEmployee persistedEmployee = employeeRepository.save(employee);
        IEmployee updateEmployee = new Employee("Ismael", "Varela", birthDate, 1);

        int affectedRows = employeeRepository.update(persistedEmployee.getId(), updateEmployee);

        IPersistedEmployee upToDateEmployee = employeeRepository.get(persistedEmployee.getId());
        assertEquals(1, affectedRows);
        assertEquals(updateEmployee.getName(), upToDateEmployee.getName());
    }
}
