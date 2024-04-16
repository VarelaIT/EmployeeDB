package EntitiesTest;

import Entities.Employee;
import Entities.IEmployee;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeEntityTest {

    private final DateFormat bdObj= new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void validConstuctor() throws ParseException {
        String name = "Ismael";
        String lastName = "Varela";
        Date birthDate = new Date(bdObj.parse("1987-10-12").getTime());
        Integer departmentId = 1;

        Employee employee = new Employee(name, lastName, birthDate, departmentId);

        assertNotNull(employee);
        assertEquals(birthDate.toString(), employee.getBirthDate().toString());
        assertEquals(name, employee.getName());
        assertEquals(lastName, employee.getLastName());
    }

    @Test
    public void inValidConstuctor() throws ParseException {
        String name = null;
        String lastName = "Varela";
        Date birthDate = new Date(bdObj.parse("1987-10-12").getTime());
        Integer departmentId = 1;

        assertThrows(RuntimeException.class, () -> {
            Employee employee = new Employee(name, lastName, birthDate, departmentId);
        });
    }

}
