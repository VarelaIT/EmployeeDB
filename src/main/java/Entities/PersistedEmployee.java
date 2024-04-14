package Entities;

import Persistence.IPersistedEmployee;

import java.sql.Date;

public class PersistedEmployee extends HumanResourse implements IPersistedEmployee {

    private int id;
    private Integer departmentId = null;
    public String department = null;

    public PersistedEmployee(int id, String name, String lastName, java.sql.Date birthDate, Integer departmentId, String department) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.departmentId= departmentId;
        this.department = department;
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
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public void setBirthDate(java.sql.Date bd) {
        this.birthDate = bd;
    }

    @Override
    public void setDepartmentId(Integer id) {
        this.departmentId = id;
    }

    public void setDepartment(String department) {
        this.lastName = department;
    }

    public String getDepartment() {
        return department;
    }

}
