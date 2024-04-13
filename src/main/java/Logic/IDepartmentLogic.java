package Logic;

import java.util.List;

public interface IDepartmentLogic {
    List<IDepartmentResponse> get();
    IDepartmentResponse get(int id);
    IDepartmentResponse save(IDepartmentRequest departmentRequest);
}
