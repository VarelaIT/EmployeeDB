package Logic;

import java.util.List;

public interface IDepartmentLogic {
    List<IDepartmentResponse> get();
    List<IDepartmentResponse> get(Integer size, Integer page);
    IDepartmentResponse get(int id);
    IDepartmentResponse save(IDepartmentRequest departmentRequest);
    IDepartmentResponse update(int id, IDepartmentRequest departmentRequest);
    IDepartmentResponse delete(int id);
}
