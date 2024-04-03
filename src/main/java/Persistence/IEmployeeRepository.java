package Persistence;

import Entities.IEmployee;

public interface IEmployeeRepository {
    void save (IEmployee employee);
    IEmployee get (int id);
}
