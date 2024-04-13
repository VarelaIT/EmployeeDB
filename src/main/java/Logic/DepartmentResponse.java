package Logic;

import Entities.IPersistedDepartment;
import Entities.PersistedDepartment;
import Logic.IDepartmentResponse;

public class DepartmentResponse extends PersistedDepartment implements IDepartmentResponse {
    public DepartmentResponse(int id, String name, String description) {
        super(id, name, description);
    }

    DepartmentResponse(IPersistedDepartment department){
        super(department.getId(), department.getName(), department.getDescription());
    }
}
