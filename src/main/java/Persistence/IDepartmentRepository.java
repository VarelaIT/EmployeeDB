package Persistence;

import Entities.IDepartment;
import Entities.IPersistedDepartment;

import java.util.List;

public interface IDepartmentRepository {
    IPersistedDepartment save(IDepartment newDepartment);
    IPersistedDepartment get(int id);
    List<IPersistedDepartment> getAll();
    void dropTable();
}
