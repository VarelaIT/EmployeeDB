package Entities;

import java.sql.Date;

public class Employee extends HumanResourse implements IEmployee{
    public int departmentId;
    public Employee(String name, String lastName, java.sql.Date birthDate) {
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;

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
