package EntitiesTest;

import Entities.Department;
import Entities.IDepartment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentEntityTest {

    @Test
    public void validConstructor(){
        var name = "HHRR";
        var description = "Human Resources";

        var department = new Department(name,description);

        assertSame(department.getName(), name);
        assertSame(department.getDescription(), description);
    }

    @Test
    public void invalidConstructor(){
        String name = null;
        String description = null;

        assertThrows(RuntimeException.class, () -> {
            Department department = new Department(name, description);
        });
    }

    @Test
    public void settingAndGettingName(){
        var name = "HHRR";
        var description = "Human Resources";
        var department = new Department(name,description);
        var newName = "Music";

        department.setName(newName);

        assertEquals(newName, department.getName());
    }

    @Test
    public void settingAndGettingDescription(){
        var name = "HHRR";
        var description = "Human Resources";
        var department = new Department(name,description);
        String newDescription = "The new description";

        department.setDescription(newDescription);

        assertEquals(newDescription, department.getDescription());
    }

}
