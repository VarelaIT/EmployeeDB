package Persistence;

import Entities.IEmployee;
import Entities.IPersistedEmployee;

import java.util.List;

public interface IEmployeeRepository {
    IPersistedEmployee save (IEmployee employee);
    IPersistedEmployee get (int id);
    List<IPersistedEmployee> get();
}
