package PersistenceTests;

import Entities.Department;
import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Persistence.DepartmentRepository;
import Persistence.IDepartmentRepository;
import Persistence.JDBC.DBConn;
import Persistence.TableSchemas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentPersistenceTest {

    public IDepartmentRepository departmentRepository;
    public String test = "test";

    @BeforeEach
    public void storageInitialization(){
        TableSchemas.dropDepartmentsTable(test);
        TableSchemas.createDepartmentsTable(test);
        departmentRepository = new DepartmentRepository(test);
    }

    @Test
    public void countingDepartments(){
        IDepartment departmentA = new Department("HHRR", "Human Resources.");
        IDepartment departmentB = new Department("R&D", "Research and Development.");

        int zero = departmentRepository.countRegisters();
        IPersistedDepartment presistedDepartmentA = departmentRepository.save(departmentA);
        IPersistedDepartment presistedDepartmentB = departmentRepository.save(departmentB);
        int count = departmentRepository.countRegisters();

        assertEquals(0, zero);
        assertEquals(2, count);
    }

    @Test
    public void persistingNewDepartment(){
        IDepartment department = new Department("HHRR", "Human Resources.");

        IPersistedDepartment result = departmentRepository.save(department);

        assertTrue(result.getId() > 0);
        assertEquals(department.getName(), result.getName());
        assertEquals(department.getDescription(), result.getDescription());
    }

    @Test
    public void persistingInvalidDepartment(){
        IDepartment department = new Department("", "Human Resources.");
        IDepartment departmentb = new Department("", "Human Resources.");
        IPersistedDepartment result = departmentRepository.save(department);

        IPersistedDepartment resultb = departmentRepository.save(departmentb);

        assertTrue(result.getId() > 0);
        assertNull(resultb);
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
        IDepartment departmentA = new Department("Data Analysis", "Analyzing Data and stuff");
        IDepartment departmentB = new Department("Design", "Graphical & visual design");
        IPersistedDepartment persistedA = departmentRepository.save(departmentA);
        IPersistedDepartment persistedB = departmentRepository.save(departmentB);

        List<IPersistedDepartment> inStorageDepartments = departmentRepository.getAll();

        assertEquals(inStorageDepartments.get(0).getName(), persistedA.getName());
        assertEquals(inStorageDepartments.get(1).getName(), persistedB.getName());
    }

    @Test
    public void getAllPersistedDepartmentsPaginated(){
        IDepartment departmentA = new Department("Data Analysis", "Analyzes data and other stuff");
        IDepartment departmentB = new Department("Design", "Graphical & visual design");
        IDepartment departmentC = new Department("Kitchen", "Where the food is cooked");
        IPersistedDepartment persistedA = departmentRepository.save(departmentA);
        IPersistedDepartment persistedB = departmentRepository.save(departmentB);
        IPersistedDepartment persistedC = departmentRepository.save(departmentC);

        List<IPersistedDepartment> inStorageDepartments = departmentRepository.getAll(2, 0);
        List<IPersistedDepartment> inStorageDepartmentsB = departmentRepository.getAll(2, 2);

        assertEquals(inStorageDepartments.get(0).getName(), persistedA.getName());
        assertEquals(inStorageDepartments.get(1).getName(), persistedB.getName());
        assertEquals(inStorageDepartmentsB.get(0).getName(), persistedC.getName());
    }

    @Test
    public void updatePersistedDepartment(){
        IDepartment department = new Department("Psychology", "The department I need to visit after dealing with Java.");
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
