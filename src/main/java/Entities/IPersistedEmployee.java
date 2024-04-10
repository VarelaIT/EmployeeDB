package Entities;

import java.util.Date;

public interface IPersistedEmployee extends IEmployee {
    int getId();
    void setId(int id);
    void setName(String name);
    void setLastName(String lastName);
    void setBirthDate(Date bd);
    void setDepartmentId(int id);
}
