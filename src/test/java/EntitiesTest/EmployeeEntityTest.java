package EntitiesTest;

import Entities.Employee;
import Entities.IEmployee;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeEntityTest {

    public IEmployee employ;
    public String name = "Ismael";
    public String lastName = "Varela";
    public java.sql.Date birthDate ;
    public Integer deparmentId = null;

    EmployeeEntityTest() throws ParseException {
        DateFormat bdObj= new SimpleDateFormat("dd-MM-yyyy");
        birthDate = new java.sql.Date(bdObj.parse("02-04-2000").getTime());
        employ = new Employee(name, lastName,  birthDate, deparmentId);
    }


    @Test
    public void gettingName(){
        assertEquals(name, employ.getName());
    }

    @Test
    public void gettingLastName(){
        assertEquals(lastName, employ.getLastName());
    }

    @Test
    public void gettingBirthDate(){
        assertEquals(birthDate.toString(), employ.getBirthDate().toString());
    }

}
