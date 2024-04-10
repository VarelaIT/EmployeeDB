package LogicTests;

import Logic.DepartmentLogic;
import Logic.DepartmentRequest;
import Logic.IDepartmentLogic;
import Logic.IDepartmentRequest;
import MockPersistence.MockDepartmentRepository;
import Persistence.ITestDepartmentRepository;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepartmentLogicTest {

    public ITestDepartmentRepository departmentRepository;

    DepartmentLogicTest(){
        Connection conn = new DBConn().getConn();
        TableSchemas.dropTestDepartmentsTable(conn);
        TableSchemas.createTestDepartmentsTable(conn);
        conn= null;
        departmentRepository = new MockDepartmentRepository();
    }

    @Test
    void saveDepartments(){
        IDepartmentRequest department = new DepartmentRequest("Data Analysis", "");
        IDepartmentLogic departmentLogic = new DepartmentLogic(departmentRepository);

        String row = departmentLogic.save(department);

        assertTrue(row.length() > 1);
    }

    @Test
    void getallDepartments(){
        IDepartmentRequest departmentA = new DepartmentRequest("Data Analysis", "");
        IDepartmentRequest departmentB = new DepartmentRequest("Design", "Graphical & visual design");
        IDepartmentLogic departmentLogic = new DepartmentLogic(departmentRepository);
        String rowA = departmentLogic.save(departmentA);
        String rowB = departmentLogic.save(departmentB);
        String payload =  rowA + rowB;



        String rawPayload = departmentLogic.get();

        assertTrue(rawPayload.length() > 1);
        assertEquals(payload, rawPayload);
    }

}
