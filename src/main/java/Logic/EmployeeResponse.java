package Logic;

import Entities.PersistedEmployee;
import Persistence.IPersistedEmployee;

public class EmployeeResponse extends PersistedEmployee implements IEmployeeResponse {
    public EmployeeResponse(IPersistedEmployee persistedEmployee) {
        super(
                persistedEmployee.getId(),
                persistedEmployee.getName(),
                persistedEmployee.getLastName(),
                persistedEmployee.getBirthDate(),
                persistedEmployee.getDepartmentId(),
                persistedEmployee.getDepartment()
        );
    }
}
