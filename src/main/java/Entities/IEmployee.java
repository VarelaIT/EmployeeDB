package Entities;

import java.util.Calendar;

public interface IEmployee {
    void setId(int index);
    int getId();
    void setName(String name);
    String getName();
    void setLastName(String lastName);
    String getLastName();
    void setBirthDate(Calendar bd);
    Calendar getBirthDate();
    void setDepartmentId(int id);
    int getDepartmentId();
}
