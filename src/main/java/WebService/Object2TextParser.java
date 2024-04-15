package WebService;

import Logic.IDepartmentResponse;
import Logic.IEmployeeResponse;
import Persistence.IPersistedEmployee;

public class Object2TextParser {

    public String employeeTableRow(IPersistedEmployee employee){
        String id = "" + employee.getId();
        String birth = employee.getBirthDate().toString();
        String row = """
            <tr>
                <td colspan='2'>$name</td>
                <td colspan='2'>$lastName</td>
                <td>$birth</td>
                <td colspan='2'>$department</td>
                <td>$id</td>
                <td colspan='2'><button
                    hx-get='./api/employee?id=$id&mode=tableForm'
                    hx-trigger='click'
                    hx-target='#table-form-container'
                    hx-swap='innerHTML'
                >Edit</button>
                <button
                    hx-delete='./api/employee?id=$id'
                    hx-trigger='click'
                    hx-target='#table-form-container'
                    hx-swap='innerHTML'
                >Delete</button></td>
            </tr>
        """;
        return row
                .replace("$name", employee.getName())
                .replace("$lastName", employee.getLastName())
                .replace("$birth", birth)
                .replace("$department", employee.getDepartment())
                .replace("$id", id);
    }

    public String employeeFormRow(IPersistedEmployee employee){
        String id = "" + employee.getId();
        String birth = employee.getBirthDate().toString();
        String tableForm = """
            <tr><form
            hx-post='./api/employee'
            hx-trigger='submit'
            hx-swap='none'
            >
            <td colspan='2'><input name='name' value='$name' placeholder='Employee name' required/></td>
            <td colspan='2'><input name='lastName' value='$lastName' placeholder='Employee lastName' required/></td>
            <td ><input name='birthDate' type='date' value='$birth' required/></td>
            <td>
                <input name='id' value='$id' type='hidden' required  style='max-width: 200px;'/>
                <select name='departmentId' required' value='$departmentId'
                   hx-get='./api/department?mode=options
                   hx-trigger='load'
                >
                    <option value='$departmentId'>$department</option>
                <select/>
            </td>
            <td colspan='2'><input type='submit' value='Edit'/></td>
            </form></tr>
        """;
        return tableForm
                .replace("$name", employee.getName())
                .replace("$lastName", employee.getLastName())
                .replace("$birth", birth)
                .replace("$department", employee.getDepartment())
                .replace("$id", id);
    }

    public String buildEmployee(String mode, IEmployeeResponse employee){
        if (mode.equals("tableForm"))
            return employeeFormRow(employee);

        return employeeTableRow(employee);
    }

    public String departmentDefaultForm(){
        return """
            <tr>
                <form
                    hx-post="./api/department"
                    hx-trigger="submit"
                    hx-swap="none"
                >
                <td colspan='2'>
                    <input name="department" placeholder="Department name" required/>
                </td>
                <td colspan='3'>
                    <input name="description" placeholder="Brief department description" required/>
                </td>
                <td>
                </td>
                <td colspan='2'>
                    <input type="submit" value="Add"/>
                </td>
                <form>
            </tr>
        """;
    }

    public String buildDepartment(String mode, IDepartmentResponse department){
        if (mode.equals("tableForm"))
            return departmentFormRow(department);
        if (mode.equals("options"))
            return departmentOptions(department);

        return departmentTableRow(department);
    }

    public String departmentFormRow(IDepartmentResponse department){
        String id = "" + department.getId();
        String tableForm = """
            <tr><form
            hx-post='./api/department'
            hx-trigger='submit'
            hx-swap='none'
            >
            <td colspan='2'><input name='department' value='$department' placeholder='Department name' required/></td>
            <td colspan='3'><input name='description' value='$description' placeholder='Brief department description' required/></td>
            <td><input name='id' value='$id' type='hidden' required  style='max-width: 200px;'/></td>
            <td colspan='2'><input type='submit' value='Edit'/></td>
            </form></tr>
        """;
        return tableForm
                .replace("$department", department.getName())
                .replace("$description", department.getDescription())
                .replace("$id", id);
    }

    public String departmentTableRow(IDepartmentResponse department){
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
                <button
                    hx-delete='./api/department?id=$id'
                    hx-trigger='click'
                    hx-target='#table-form-container'
                    hx-swap='innerHTML'
                >Delete</button></td>
            </tr>
        """;
        return row
                .replace("$department", department.getName())
                .replace("$description", department.getDescription())
                .replace("$id", id);
    }

    public String departmentOptions(IDepartmentResponse department){
        String id = "" + department.getId();
        String option = """
            <option value='$id'>$department</option>
        """;
        return option
            .replace("$department", department.getName())
            .replace("$id", id);
    }
}
