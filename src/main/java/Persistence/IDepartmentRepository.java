package Persistence;

import Entities.IDepartment;
import Entities.IPersistedDepartment;

import java.sql.SQLException;
import java.util.List;

public interface IDepartmentRepository {
    IPersistedDepartment save(IDepartment newDepartment);
    IPersistedDepartment get(int id);
    List<IPersistedDepartment> getAll();
    List<IPersistedDepartment> getAll(int size, int page);
    IPersistedDepartment update(int id, IDepartment department);
    IPersistedDepartment delete(int id);
    int countRegisters();
}
