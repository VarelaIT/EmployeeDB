package PersistenceTests;

import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Persistence.IDepartmentRepository;

import java.sql.Connection;

public interface ITestDepartmentRepository extends IDepartmentRepository {
    void dropTable();

}
