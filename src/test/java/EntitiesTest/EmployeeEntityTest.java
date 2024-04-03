package EntitiesTest;

import Entities.Employee;
import Entities.IEmployee;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


public class EmployeeEntityTest {

    public IEmployee employ;
    public String name = "Ismael";
    public String lastName = "Varela";
    public Calendar birthDate = Calendar.getInstance();

    EmployeeEntityTest() {
        birthDate.set(1987, 2, 8);
        employ = new Employee(name, lastName,  birthDate);
    }

    @Test
    public void constructor(){
        assertSame(employ.getName(), name);
        assertSame(employ.getLastName(), lastName);
        assertEquals(employ.getBirthDate().toString(), birthDate.toString());
    }

    @Test
    public void settingAndGettingId(){
        int id = 0;

        employ.setId(id);

        assertEquals(id, employ.getId());
    }
    @Test
    public void settingAndGettingName(){
        String newName = "Manases";

        employ.setName(newName);

        assertEquals(newName, employ.getName());
    }

    @Test
    public void settingAndGettingLastName(){
        String newLastName = "Lovera";

        employ.setLastName(newLastName);

        assertEquals(newLastName, employ.getLastName());
    }

    @Test
    public void settingAndGettingBirthDate(){
        Calendar newBD = Calendar.getInstance();
        newBD.set(2003, 12, 18);

        employ.setBirthDate(newBD);

        assertEquals(newBD, employ.getBirthDate());
    }

    @Test
    public void settingAndGettingDepartmentId(){
        int newDpartmentId = 1;

        employ.setDepartmentId(newDpartmentId);

        assertEquals(newDpartmentId, employ.getDepartmentId());
    }

}
