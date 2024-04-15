package LogicTests;


import Logic.*;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeLogicTest {

    private IEmployeeLogic employeeLogic;
    public EmployeeLogicTest(){
        Connection conn = new DBConn().getConn();
        TableSchemas.dropEmployeesTable(conn);
        TableSchemas.createEmployeesTable(conn);
        conn= null;
        employeeLogic = new EmployeeLogic("test");
    }

    @Test
    public void createEmployee() throws ParseException {
        IEmployeeRequest newEmployee = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);

        IEmployeeResponse storedEmployee = employeeLogic.save(newEmployee);

        assertEquals(newEmployee.getName(), storedEmployee.getName());
        assertTrue(storedEmployee.getId() > 0);
    }

    @Test
    public void updateEmployee() throws ParseException {
        IEmployeeRequest newEmployee = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeResponse storedEmployee = employeeLogic.save(newEmployee);
        IEmployeeRequest newEmployeeVersion = new EmployeeRequest("Ismael", "Varela", "1988-5-15", 1);

        IEmployeeResponse updatedEmployee = employeeLogic.update(storedEmployee.getId(), newEmployeeVersion);

        assertEquals(newEmployee.getName(), storedEmployee.getName());
        assertEquals(storedEmployee.getId(), updatedEmployee.getId());
        assertNotEquals(storedEmployee.getName(), updatedEmployee.getName());
    }

    @Test
    public void getOneEmployee() throws ParseException {
        IEmployeeRequest newEmployee = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeResponse storedEmployee = employeeLogic.save(newEmployee);

        IEmployeeResponse retrievedEmployee = employeeLogic.get(storedEmployee.getId());

        assertEquals(storedEmployee.getId(), retrievedEmployee.getId());
        assertEquals(storedEmployee.getName(), retrievedEmployee.getName());
    }

    @Test
    public void getManyEmployee() throws ParseException {
        IEmployeeRequest employeeA = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeRequest employeeB = new EmployeeRequest("Samuel", "Ureña", "2004-5-15", 1);
        IEmployeeResponse storedEmployeeA = employeeLogic.save(employeeA);
        IEmployeeResponse storedEmployeeB = employeeLogic.save(employeeB);

        List<IEmployeeResponse> retrievedEmployees = employeeLogic.get();

        assertTrue(retrievedEmployees.size() > 1);
    }

    @Test
    public void deleteOneEmployee() throws ParseException {
        IEmployeeRequest newEmployee = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeResponse storedEmployee = employeeLogic.save(newEmployee);

        IEmployeeResponse deletedEmployee = employeeLogic.delete(storedEmployee.getId());

        IEmployeeResponse notFoundEmployee = employeeLogic.get(storedEmployee.getId());
        assertEquals(storedEmployee.getId(), deletedEmployee.getId());
        assertNull(notFoundEmployee);
    }
}
