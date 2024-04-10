package Entities;

import java.sql.Date;

public class PersistedEmployee extends HumanResourse implements IPersistedEmployee {

    private int id;
    private Integer departmentId = null;

    public PersistedEmployee(int id, String name, String lastName, java.util.Date birthDate, Integer departmentId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.departmentId= departmentId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Date getBirthDate() {
        return (Date) birthDate;
    }

    @Override
    public Integer getDepartmentId() {
        return departmentId;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setLastName(String lastName) {

    }

    @Override
    public void setBirthDate(java.util.Date bd) {

    }

    @Override
    public void setDepartmentId(int id) {

    }
}
