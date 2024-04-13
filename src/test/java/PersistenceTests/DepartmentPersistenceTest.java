package PersistenceTests;

import Entities.Department;
import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Persistence.DepartmentRepository;
import Persistence.IDepartmentRepository;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentPersistenceTest {

    public IDepartmentRepository departmentRepository;

    DepartmentPersistenceTest(){
        Connection conn = new DBConn().getConn();
        TableSchemas.dropDepartmentsTable(conn);
        TableSchemas.createDepartmentsTable(conn);
        conn= null;
        departmentRepository = new DepartmentRepository();
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

    @Test
    public void updatePersistedDepartment(){
        IDepartment department = new Department("Psicology", "The department I need to visit after dealing with Java.");
        IPersistedDepartment persistedDepartment = departmentRepository.save(department);
        department.setName("Health care");

        IPersistedDepartment updatedDepartment = departmentRepository.update(persistedDepartment.getId(), department);

        assertEquals(updatedDepartment.getName(), department.getName());
        assertNotEquals(updatedDepartment.getName(), persistedDepartment.getName());
    }

    @Test
    public void deletePersistedDepartment(){
        IDepartment department = new Department("Psicology", "The department I need to visit after dealing with Java.");
        IPersistedDepartment persistedDepartment = departmentRepository.save(department);

        IPersistedDepartment deletedDepartment = departmentRepository.delete(persistedDepartment.getId());
        IPersistedDepartment notfoundDepartment = departmentRepository.get(persistedDepartment.getId());

        assertEquals(persistedDepartment.getName(), deletedDepartment.getName());
        assertNull(notfoundDepartment);
    }
}
