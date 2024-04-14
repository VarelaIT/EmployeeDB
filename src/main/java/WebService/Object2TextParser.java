package WebService;

import Entities.IPersistedDepartment;
import Logic.IDepartmentResponse;
import Logic.IEmployeeResponse;
import Persistence.IPersistedEmployee;

public class Object2TextParser {

    public String employeeTableRow(IPersistedEmployee employee){
        String id = "" + employee.getId();
        String age = "" + employee.getId();
        String row = """
            <tr>
                <td colspan='2'>$name</td>
                <td colspan='2'>$lastName</td>
                <td colspan='2'>$age</td>
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
                .replace("$age", age)
                .replace("$department", employee.getDepartment())
                .replace("$id", id);
    }

    public String employeeFormRow(IPersistedEmployee employee){
        String id = "" + employee.getId();
        String age = "" + employee.getBirthDate();
        String tableForm = """
            <tr><form
            hx-post='./api/employee'
            hx-trigger='submit'
            hx-swap='none'
            >
            <td colspan='2'><input name='name' value='$name' placeholder='Employee name' required/></td>
            <td colspan='2'><input name='lastName' value='$lastName' placeholder='Employee lastName' required/></td>
            <td colspan='2'><input name='age' value='$age' placeholder='Employee lastName' required/></td>
            <td>
                <input name='id' value='$id' type='hidden' required  style='max-width: 200px;'/>
                <select name='departmentId' required'>
                     <option value='$departmentId'>$department</option>
                     <div 
                        hx-get='/EmployeeDB/api/department?mode=options
                     >
                     </div>
                <select/>
            </td>
            <td colspan='2'><input type='submit' value='Edit'/></td>
            </form></tr>
        """;
        return tableForm
                .replace("$name", employee.getName())
                .replace("$latName", employee.getLastName())
                .replace("$age", age)
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

        return departmentTableRow(department);
    }

    public String departmentFormRow(IPersistedDepartment department){
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

    public String departmentOptions(IPersistedDepartment department){
        String id = "" + department.getId();
        String option = """
            <option value='$id'>$department</option>
        """;
        return option
            .replace("$department", department.getName())
            .replace("$id", id);
    }
}
