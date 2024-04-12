package PersistenceTests;

import Entities.IEmployee;
import Entities.Employee;
import Entities.IPersistedEmployee;
import MockPersistence.MockEmployeeRepository;
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
        TableSchemas.dropTestEmployeesTable(conn);
        TableSchemas.createTestEmployeeTable(conn);
        conn= null;
        employeeRepository = new MockEmployeeRepository();
    }

    @Test
    public void persistEmployee() throws ParseException {
        String name = "Ismael";
        String lastName = "Varela";
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1987").getTime());
        Integer departmentID = null;
        IEmployee employee = new Employee(name, lastName, birthDate, departmentID);

        IPersistedEmployee persistedEmployee = employeeRepository.save(employee);

        assertEquals(name, persistedEmployee.getName());
        assertEquals(lastName, persistedEmployee.getLastName());
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
}
