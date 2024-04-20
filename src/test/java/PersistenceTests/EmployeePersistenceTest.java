package PersistenceTests;

import Entities.IEmployee;
import Entities.Employee;
import Persistence.IPersistedEmployee;
import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.TableSchemas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeePersistenceTest {

    private IEmployeeRepository employeeRepository;
    private final DateFormat bdObj= new SimpleDateFormat("dd-MM-yyyy");

    public String test = "test";

    @BeforeEach
    public void employeeStorageInitialization(){
        TableSchemas.dropEmployeesTable(test);
        TableSchemas.createEmployeesTable(test);
        employeeRepository = new EmployeeRepository(test);
    }

    @Test
    public void countEmployeeRegisters() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1999").getTime());
        IEmployee employeeA = new Employee("Juan", "Rodriguez", birthDate, 1);
        IEmployee employeeB = new Employee("Elias", "Mejia", birthDate, 1);

        int zero = employeeRepository.countRegisters();
        IPersistedEmployee newEmployeeA = employeeRepository.save(employeeA);
        IPersistedEmployee newEmployeeB = employeeRepository.save(employeeB);
        int count = employeeRepository.countRegisters();

        assertEquals(0, zero);
        assertEquals(2, count);
    }

    @Test
    public void persistEmployee() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1987").getTime());
        Integer departmentID = 1;
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
        IEmployee employee = new Employee("Juan Elias", "Rodriguez", birthDate, 1);
        IPersistedEmployee newEmployee = employeeRepository.save(employee);

        IPersistedEmployee sameEmployee = employeeRepository.get(newEmployee.getId());

        assertEquals(newEmployee.getId(), sameEmployee.getId());
        assertEquals(newEmployee.getName(), sameEmployee.getName());
        assertEquals(newEmployee.getBirthDate(), sameEmployee.getBirthDate());
    }

    @Test
    public void getPersistedEmployeesList() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1999").getTime());
        IEmployee employeeA = new Employee("Juan", "Rodriguez", birthDate, 1);
        IEmployee employeeB = new Employee("Elias", "Mejia", birthDate, 1);
        IPersistedEmployee newEmployeeA = employeeRepository.save(employeeA);
        IPersistedEmployee newEmployeeB = employeeRepository.save(employeeB);

        List<IPersistedEmployee> listOfEmployees = employeeRepository.get();

        assertEquals(newEmployeeA.getName(), listOfEmployees.get(0).getName());
        assertEquals(newEmployeeB.getName(), listOfEmployees.get(1).getName());
    }

    @Test
    public void getPersistedEmployeesListPaginated() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1999").getTime());
        IEmployee employeeA = new Employee("Juan", "Rodriguez", birthDate, 1);
        IEmployee employeeB = new Employee("Elias", "Mejia", birthDate, 1);
        IEmployee employeeC = new Employee("Kendry", "Grullon", birthDate, 1);
        IPersistedEmployee newEmployeeA = employeeRepository.save(employeeA);
        IPersistedEmployee newEmployeeB = employeeRepository.save(employeeB);
        IPersistedEmployee newEmployeeC = employeeRepository.save(employeeC);

        List<IPersistedEmployee> listOfEmployees = employeeRepository.get(2, 0);
        List<IPersistedEmployee> listOfEmployeesB = employeeRepository.get(2, 2);

        assertEquals(newEmployeeA.getName(), listOfEmployees.get(0).getName());
        assertEquals(newEmployeeB.getName(), listOfEmployees.get(1).getName());
        assertEquals(newEmployeeC.getName(), listOfEmployeesB.get(0).getName());
    }

    @Test
    public void  updatePersistedEmployee() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1987").getTime());
        IEmployee employee = new Employee("Lary", "Figurereo", birthDate, 1);
        IPersistedEmployee persistedEmployee = employeeRepository.save(employee);
        IEmployee updateEmployee = new Employee("Ismael", "Varela", birthDate, 1);

        int affectedRows = employeeRepository.update(persistedEmployee.getId(), updateEmployee);

        IPersistedEmployee upToDateEmployee = employeeRepository.get(persistedEmployee.getId());
        assertEquals(1, affectedRows);
        assertEquals(updateEmployee.getName(), upToDateEmployee.getName());
    }

    @Test
    public void deletePersistedEmployee() throws ParseException {
        java.sql.Date birthDate = new java.sql.Date(bdObj.parse("02-08-1987").getTime());
        IEmployee employee = new Employee("Ramon", "De Leon", birthDate, 1);
        IPersistedEmployee persistedEmployee = employeeRepository.save(employee);

        IPersistedEmployee deletedEmployee = employeeRepository.delete(persistedEmployee.getId());

        IPersistedEmployee notfoundEmployee = employeeRepository.get(persistedEmployee.getId());
        assertEquals(persistedEmployee.getName(), deletedEmployee.getName());
        assertNull(notfoundEmployee);
    }
}
