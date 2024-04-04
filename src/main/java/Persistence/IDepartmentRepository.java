package Persistence;

import Entities.IDepartment;
import Entities.IPersistedDepartment;

import java.sql.Connection;
import java.util.List;

public interface IDepartmentRepository {
    IPersistedDepartment save(Connection conn, IDepartment newDepartment);
    IPersistedDepartment get(Connection conn, int id);
    List<IPersistedDepartment> getAll(Connection conn);

}
