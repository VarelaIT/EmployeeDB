package Persistence;

import Entities.IEmployee;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeRepository {
    IPersistedEmployee save(IEmployee employee);
    IPersistedEmployee get(int id);
    List<IPersistedEmployee> get();
    List<IPersistedEmployee> get(int size, int page);
    int update(int id, IEmployee employee);
    IPersistedEmployee delete(int id);
}
