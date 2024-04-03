package Persistence;

import Entities.IDepartment;
import Entities.IPersistedDepartment;

import java.sql.Connection;

public interface IDepartmentRepository {
    IPersistedDepartment save(Connection conn, IDepartment newDepartment);
    IPersistedDepartment get(Connection conn, int id);
}
