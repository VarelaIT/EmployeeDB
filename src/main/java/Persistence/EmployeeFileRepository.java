package Persistence;

import Entities.IEmployee;
import Entities.IPersistedEmployee;

import java.util.List;

public class EmployeeFileRepository implements IEmployeeRepository {

    @Override
    public IPersistedEmployee save(IEmployee employee) {

        return null;
    }

    @Override
    public IPersistedEmployee get(int id) {
        return null;
    }

    @Override
    public List<IPersistedEmployee> get() {
        return null;
    }
}
