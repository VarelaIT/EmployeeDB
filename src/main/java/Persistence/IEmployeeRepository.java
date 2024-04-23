package Persistence;

import Entities.IEmployee;

import java.sql.SQLException;
import java.util.List;

public interface IEmployeeRepository {
    Integer chunkData(int processId, String chunk);
    IPersistedEmployee save(IEmployee employee);
    IPersistedEmployee get(int id);
    List<IPersistedEmployee> get();
    List<IPersistedEmployee> get(int size, int page);
    List<IPersistedEmployee> find(String pattern);
    int update(int id, IEmployee employee);
    IPersistedEmployee delete(int id);
    int countRegisters();
}
