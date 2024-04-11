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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeePersistenceTest {

    private final IEmployeeRepository employeeRepository;

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
        DateFormat bdObj= new SimpleDateFormat("dd-MM-yyyy");
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1987").getTime());
        Integer departmentID = null;
        IEmployee employee = new Employee(name, lastName, birthDate, departmentID);

        IPersistedEmployee persistedEmployee = employeeRepository.save(employee);


        assertEquals(name, persistedEmployee.getName());
        assertEquals(lastName, persistedEmployee.getLastName());
        assertEquals(birthDate.toString(), persistedEmployee.getBirthDate().toString());
        assertEquals(departmentID, persistedEmployee.getDepartmentId()); // database returns 0 instead of null
    }
}
