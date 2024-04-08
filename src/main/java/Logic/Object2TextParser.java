package Logic;

import Entities.IPersistedDepartment;

public class Object2TextParser {
    public String departmentTableRow(IPersistedDepartment department){
        return "<tr><td>" + department.getName() + "</td><td>"
                + department.getDescription() + "</td><td>"
                + department.getId() + "</td></tr>";
    }
}
