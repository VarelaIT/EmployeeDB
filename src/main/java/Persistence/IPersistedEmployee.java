package Persistence;

import Entities.IEmployee;


public interface IPersistedEmployee extends IEmployee {
    int getId();
    void setId(int id);
    void setName(String name);
    void setLastName(String lastName);
    void setBirthDate(java.sql.Date bd);
    void setDepartmentId(Integer id);
    String getDepartment();
    void setDepartment(String department);
}
