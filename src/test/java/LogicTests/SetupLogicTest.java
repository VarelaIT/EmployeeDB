package LogicTests;

import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Logic.IDepartmentLogic;
import Logic.IStorageSetup;
import Logic.Setup;
import Persistence.DepartmentRepository;
import Persistence.TableSchemas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetupLogicTest {

    public String test = "test";

    @BeforeEach
    public void setUp(){
        TableSchemas.dropDepartmentsTable(test);
        TableSchemas.createDepartmentsTable(test);
    }

    @Test
    public void insertingDefaultDepartments(){
        IStorageSetup setupManager = new Setup(test);

        boolean result = setupManager.defaultDepartments();

        List<IPersistedDepartment> departmentList = new DepartmentRepository(test).getAll();
        assertEquals(7, departmentList.size());
    }
}
