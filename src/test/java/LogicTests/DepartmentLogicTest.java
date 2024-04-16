package LogicTests;

import java.util.List;
import Logic.DepartmentLogic;
import Logic.DepartmentRequest;
import Logic.IDepartmentLogic;
import Logic.IDepartmentRequest;
import Logic.IDepartmentResponse;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentLogicTest {

    public IDepartmentLogic departmentLogic;
    public String test = "test";

    DepartmentLogicTest(){
        TableSchemas.dropDepartmentsTable(test);
        TableSchemas.createDepartmentsTable(test);
        departmentLogic = new DepartmentLogic(test);
    }

    @Test
    void saveDepartments(){
        IDepartmentRequest department = new DepartmentRequest("Data Analysis", "");

        IDepartmentResponse  storedDepartment = departmentLogic.save(department);

        assertEquals(department.getName(), storedDepartment.getName());
    }

    @Test
    void updateDepartments(){
        IDepartmentRequest department = new DepartmentRequest("Data Analysis", "");
        IDepartmentResponse  storedDepartment = departmentLogic.save(department);
        IDepartmentRequest updatedVersion = new DepartmentRequest("Data Analytics", "had a typo");

        IDepartmentResponse  updatedDepartment = departmentLogic.update(storedDepartment.getId(), updatedVersion);

        assertEquals(updatedVersion.getName(), updatedDepartment.getName());
        assertEquals(updatedVersion.getDescription(), updatedDepartment.getDescription());
        assertNotEquals(storedDepartment.getName(), updatedDepartment.getName());
    }

    @Test
    void getOneDepartment(){
        IDepartmentRequest department = new DepartmentRequest("R&D", "Research and development");
        IDepartmentResponse storedDepartment = departmentLogic.save(department);

        IDepartmentResponse retrivedDepartment = departmentLogic.get(storedDepartment.getId());

        assertEquals(retrivedDepartment.getName(), storedDepartment.getName());
    }

    @Test
    void getallDepartments(){
        IDepartmentRequest departmentA = new DepartmentRequest("Data Analysis", "");
        IDepartmentRequest departmentB = new DepartmentRequest("Design", "Graphical & visual design");
        IDepartmentResponse storedDepartmentA = departmentLogic.save(departmentA);
        IDepartmentResponse storedDepartmentB = departmentLogic.save(departmentB);

        List<IDepartmentResponse> instorageDepartments = departmentLogic.get();

        assertEquals(storedDepartmentA.getId(), instorageDepartments.get(0).getId());
        assertEquals(storedDepartmentA.getId(), instorageDepartments.get(0).getId());
        assertEquals(storedDepartmentB.getName(), instorageDepartments.get(1).getName());
        assertEquals(storedDepartmentB.getName(), instorageDepartments.get(1).getName());
    }

    @Test
    void deleteOneDepartment(){
        IDepartmentRequest department = new DepartmentRequest("A&V", "Aliments and beverages");
        IDepartmentResponse storedDepartment = departmentLogic.save(department);

        IDepartmentResponse deletedDepartment = departmentLogic.delete(storedDepartment.getId());

        IDepartmentResponse notFoundDepartment = departmentLogic.get(storedDepartment.getId());
        assertEquals(storedDepartment.getName(), deletedDepartment.getName());
        assertNull(notFoundDepartment);
    }

    @Test
    void deleteNoneExistingDepartment(){
        int id = 100;

        IDepartmentResponse notFoundDepartment= departmentLogic.delete(id);

        assertNull(notFoundDepartment);
    }

}
