package PersistenceTests;

import Entities.Department;
import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepartmentPersistenceTest {

    public Connection conn = new DBConn().getConn();
    public ITestDepartmentRepository departmentRepository;

    DepartmentPersistenceTest(){
        TableSchemas.dropTestDepartmentTable(conn);
        TableSchemas.createTestDepartmentTable(conn);
        departmentRepository = new TestDepartmentRepository();
    }

    @Test
    public void persistingNewDepartment(){
        IDepartment department = new Department("HHRR", "Human Resources.");

        IPersistedDepartment result = departmentRepository.save(conn, department);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void getPersistedDepartment(){
        IDepartment department = new Department("R&D", "Research and Development.");
        IPersistedDepartment persisted = departmentRepository.save(conn, department);
        int id = persisted.getId();

        IPersistedDepartment inStorageDepartment = departmentRepository.get(conn, id);

        assertEquals(persisted.getId(), inStorageDepartment.getId());
        assertEquals(persisted.getName(), inStorageDepartment.getName());
        assertEquals(persisted.getDescription(), inStorageDepartment.getDescription());
    }
}
