package Logic;

public class DepartmentRequest implements IDepartmentRequest {

    protected String name;
    protected String description;
    public DepartmentRequest(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean verifyInput() {
        if (name == null || description == null)
            return false;

        if (name.length() < 2 || description.length() < 6)
            return false;

        return true;
    }
}
