package WebService;

import Entities.IPersistedDepartment;
import Logic.IDepartmentResponse;

public class Object2TextParser {

    public String buildDepartment(String mode, IDepartmentResponse department){
        if (mode.equals("tableForm"))
            return departmentFormRow(department);

        return departmentTableRow(department);
    }

    public String departmentFormRow(IPersistedDepartment department){
        String id = "" + department.getId();
        String tableForm = """
            <tr><form
            hx-put='./api/department'
            hx-trigger='submit'
            hx-swap='none'
            >
            <td colspan='2'><input name='department' value='$department' placeholder='Department name' required/></td>
            <td colspan='3'><input name='description' value='$description' placeholder='Brief department description' required/></td>
            <td><input name='id' value='$id' required disabled style='max-width: 200px;'/></td>
            <td colspan='2'><input type='submit' value='Edit'/></td>
            </form></tr>
        """;
        return tableForm
                .replace("$department", department.getName())
                .replace("$description", department.getDescription())
                .replace("$id", id);
    }

    public String departmentTableRow(IPersistedDepartment department){
        String id = "" + department.getId();
        String row = """
            <tr>
                <td colspan='2'>$department</td>
                <td colspan='3'>$description</td>
                <td>$id</td>
                <td colspan='2'><button
                    hx-get='./api/department?id=$id&mode=tableForm'
                    hx-trigger='click'
                    hx-target='#table-form-container'
                    hx-swap='innerHTML'
                >Edit</button>
                <button>Delete</button></td>
            </tr>
        """;
        return row
            .replace("$department", department.getName())
            .replace("$description", department.getDescription())
            .replace("$id", id);
    }
}
