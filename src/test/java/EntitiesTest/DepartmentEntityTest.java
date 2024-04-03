package EntitiesTest;

import Entities.Department;
import Entities.IDepartment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DepartmentEntityTest {

    public IDepartment department;
    public String name = "HHRR";
    public String description = "Human Resourses";

    DepartmentEntityTest() {
        department = new Department(name, description);
    }

    @Test
    public void constructor(){
        assertSame(department.getName(), name);
        assertSame(department.getDescription(), description);
    }

    @Test
    public void settingAndGettingName(){
        String newName = "Manases";

        department.setName(newName);

        assertEquals(newName, department.getName());
    }

    @Test
    public void settingAndGettingDescription(){
        String newDescription = "Lovera";

        department.setDescription(newDescription);

        assertEquals(newDescription, department.getDescription());
    }

}
