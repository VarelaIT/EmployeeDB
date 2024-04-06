package PersistenceTests;

import Entities.Department;
import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DepartmentPersistenceTest {

    public ITestDepartmentRepository departmentRepository;

    DepartmentPersistenceTest(){
        Connection conn = new DBConn().getConn();
        TableSchemas.dropTestDepartmentTable(conn);
        TableSchemas.createTestDepartmentTable(conn);
        conn= null;
        departmentRepository = new MockDepartmentRepository();
    }

    @Test
    public void persistingNewDepartment(){
        IDepartment department = new Department("HHRR", "Human Resources.");

        IPersistedDepartment result = departmentRepository.save(department);

        assertTrue(result.getId() > 0);
    }

    @Test
    public void getPersistedDepartment() {
        IDepartment department = new Department("R&D", "Research and Development.");
        IPersistedDepartment persisted = departmentRepository.save(department);
        int id = persisted.getId();

        IPersistedDepartment inStorageDepartment = departmentRepository.get(id);

        assertEquals(persisted.getId(), inStorageDepartment.getId());
        assertEquals(persisted.getName(), inStorageDepartment.getName());
        assertEquals(persisted.getDescription(), inStorageDepartment.getDescription());
    }

    @Test
    public void getAllPersistedDepartments(){
        IDepartment departmentA = new Department("Data Analysis", "");
        IDepartment departmentB = new Department("Design", "Graphical & visual design");
        IPersistedDepartment persistedA = departmentRepository.save(departmentA);
        IPersistedDepartment persistedB = departmentRepository.save(departmentB);

        List<IPersistedDepartment> inStorageDepartments = departmentRepository.getAll();

        assertTrue(inStorageDepartments.size() > 1);
    }
}