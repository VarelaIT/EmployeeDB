package Logic;

public interface IDepartmentLogic {
    String get();
    String get(int id);
    String save(IDepartmentRequest departmentRequest);
}
