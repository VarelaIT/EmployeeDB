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
        //Implement latter
        return true;
    }
}
