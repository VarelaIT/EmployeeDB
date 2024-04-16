package Entities;

public class Department extends AbstractDepartment implements IDepartment{
    public Department(String name, String description) {
        if (name == null | description == null)
            throw new RuntimeException("The department values are invalid.");

        this.name = name;
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
