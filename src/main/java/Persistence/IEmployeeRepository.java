package Persistence;

import Entities.Employee;
import Entities.IEmployee;
import Entities.IPersistedEmployee;

import java.util.List;

public interface IEmployeeRepository {
    IPersistedEmployee save (IEmployee employee);
    IPersistedEmployee get (int id);
    List<IPersistedEmployee> get();
    int update(int id, IEmployee employee);
    void dropTable();
}
