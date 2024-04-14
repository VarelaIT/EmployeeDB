package LogicTests;


import Logic.*;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeLogicTest {

    private IEmployeeLogic employeeLogic;
    public EmployeeLogicTest(){
        Connection conn = new DBConn().getConn();
        TableSchemas.dropEmployeesTable(conn);
        TableSchemas.createEmployeesTable(conn);
        conn= null;
        employeeLogic = new EmployeeLogic();
    }

    @Test
    public void createEmployee() throws ParseException {
        IEmployeeRequest newEmployee = new EmployeeRequest("Isrrael", "Adesagna", "2000-5-15", 1);

        IEmployeeResponse storedEmployee = employeeLogic.save(newEmployee);

        assertEquals(newEmployee.getName(), storedEmployee.getName());
        assertTrue(storedEmployee.getId() > 0);
    }
}
