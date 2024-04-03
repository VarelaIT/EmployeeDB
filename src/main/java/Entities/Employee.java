package Entities;

import java.util.Calendar;

public class Employee extends HumanResourse implements IEmployee{
    public int departmentId;
    public Employee(String name, String lastName, Calendar birthDate) {
        super(name, lastName, birthDate);
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

    public void setBirthDate(Calendar bd) {
        this.birthDate = bd;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setDepartmentId(int id) {
        departmentId = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

}
