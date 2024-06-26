package LogicTests;


import Logic.*;
import Persistence.TableSchemas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeLogicTest {

    private IEmployeeLogic employeeLogic;
    public String test = "test";

    @BeforeEach
    public void setEmployeeStorage(){
        TableSchemas.dropEmployeesTable(test);
        TableSchemas.createEmployeesTable(test);
        TableSchemas.createEmployeesView(test);
        employeeLogic = new EmployeeLogic(test);
    }

    @Test
    public void johnDoeUpdateIssue(){
        IEmployeeRequest employee = new EmployeeRequest("John", "Doe", "1990-05-15", 1);
        IEmployeeRequest updatedEmployee = new EmployeeRequest("Johnnie", "Doe", "1990-05-15", 1);
        IEmployeeResponse storedEmployee = employeeLogic.save(employee);

        IEmployeeResponse updatedStoredEmployee = employeeLogic.update(storedEmployee.getId(), updatedEmployee);

        assertNotEquals(storedEmployee.getName(), updatedStoredEmployee.getName());
        assertEquals(storedEmployee.getLastName(), updatedStoredEmployee.getLastName());
        assertEquals(storedEmployee.getBirthDate(), updatedStoredEmployee.getBirthDate());
    }

    @Test
    public void findEmployee(){
        IEmployeeRequest employee = new EmployeeRequest("Ismael", "Varela", "1988-5-15", 1);
        IEmployeeRequest employeeA = new EmployeeRequest("Lary", "Figuereo", "2000-5-15", 1);
        IEmployeeRequest employeeB = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeResponse storedEmployee = employeeLogic.save(employee);
        IEmployeeResponse storedEmployeeA = employeeLogic.save(employeeA);
        IEmployeeResponse storedEmployeeB = employeeLogic.save(employeeB);

        List<IEmployeeResponse> employeeList = employeeLogic.find("ismael");
        List<IEmployeeResponse> employeeListA = employeeLogic.find("figuereo");
        List<IEmployeeResponse> employeeListB = employeeLogic.find("el ad");

        assertEquals(storedEmployee.getName(), employeeList.get(0).getName());
        assertEquals(storedEmployeeA.getName(), employeeListA.get(0).getName());
        assertEquals(storedEmployeeB.getName(), employeeListB.get(0).getName());
    }

    @Test
    public void createEmployee() throws ParseException {
        IEmployeeRequest newEmployee = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);

        IEmployeeResponse storedEmployee = employeeLogic.save(newEmployee);

        assertEquals(newEmployee.getName(), storedEmployee.getName());
        assertTrue(storedEmployee.getId() > 0);
    }

    @Test
    public void createInvalidEmployee(){
        IEmployeeRequest noNameEmployee = new EmployeeRequest(null, "Adesagna", "2000-5-15", 1);
        IEmployeeRequest not18Employee = new EmployeeRequest("Isrrael", "Adesagna", "2010-5-15", 1);

        IEmployeeResponse invalidNameEmployee = employeeLogic.save(noNameEmployee);
        IEmployeeResponse invalidDateEmployee = employeeLogic.save(not18Employee);

        assertNull(invalidNameEmployee);
        assertNull(invalidDateEmployee);
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
    public void updateInvalidEmployee() throws ParseException {
        IEmployeeRequest newEmployee = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeResponse storedEmployee = employeeLogic.save(newEmployee);
        IEmployeeRequest noNameEmployee = new EmployeeRequest(null, "Adesagna", "2000-5-15", 1);
        IEmployeeRequest not18Employee = new EmployeeRequest("Isrrael", "Adesagna", "2010-5-15", 1);

        IEmployeeResponse updatedEmployee = employeeLogic.update(storedEmployee.getId(), noNameEmployee);
        IEmployeeResponse updatedEmployeeB = employeeLogic.update(storedEmployee.getId(), not18Employee);

        assertNull(updatedEmployee);
        assertNull(updatedEmployeeB);
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
    public void getManyEmployee(){
        IEmployeeRequest employeeA = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeRequest employeeB = new EmployeeRequest("Samuel", "Ureña", "2004-5-15", 1);
        IEmployeeResponse storedEmployeeA = employeeLogic.save(employeeA);
        IEmployeeResponse storedEmployeeB = employeeLogic.save(employeeB);

        List<IEmployeeResponse> retrievedEmployees = employeeLogic.get();

        assertEquals(storedEmployeeA.getName(), retrievedEmployees.get(0).getName());
        assertEquals(storedEmployeeB.getName(), retrievedEmployees.get(1).getName());
    }

    @Test
    public void getManyEmployeePaginated(){
        IEmployeeRequest employeeA = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);
        IEmployeeRequest employeeB = new EmployeeRequest("Samuel", "Ureña", "2004-5-15", 1);
        IEmployeeRequest employeeC = new EmployeeRequest("Ismael", "Varela", "2004-5-15", 1);
        IEmployeeResponse storedEmployeeA = employeeLogic.save(employeeA);
        IEmployeeResponse storedEmployeeB = employeeLogic.save(employeeB);
        IEmployeeResponse storedEmployeeC = employeeLogic.save(employeeC);

        List<IEmployeeResponse> retrievedEmployees = employeeLogic.get(2,1);
        List<IEmployeeResponse> retrievedEmployeesB = employeeLogic.get(2,2);

        assertEquals(storedEmployeeA.getName(), retrievedEmployees.get(0).getName());
        assertEquals(storedEmployeeB.getName(), retrievedEmployees.get(1).getName());
        assertEquals(storedEmployeeC.getName(), retrievedEmployeesB.get(0).getName());
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

    @Test
    public void deleteNoneExisingEmployee() throws ParseException {
        int id = 100;

        IEmployeeResponse notFoundEmployee = employeeLogic.delete(id);

        assertNull(notFoundEmployee);
    }
}
