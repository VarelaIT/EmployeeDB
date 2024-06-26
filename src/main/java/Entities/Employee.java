package Entities;

import java.sql.Date;

public class Employee extends HumanResourse implements IEmployee{
    public Integer departmentId = null;

    public Employee(String name, String lastName, java.sql.Date birthDate, Integer dpId) {
        if (name == null | lastName == null | birthDate == null | dpId == null)
            throw new RuntimeException("The employee can not be instanced because invalid parameter.");

        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.departmentId = dpId;
    }

    public void setId(int index) {
        id = index;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthDate(Date bd) {
        this.birthDate = bd;
    }

    public java.sql.Date getBirthDate() {
        return (Date) birthDate;
    }

    public void setDepartmentId(int id) {
        departmentId = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

}
