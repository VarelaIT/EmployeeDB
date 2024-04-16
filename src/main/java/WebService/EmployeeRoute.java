package WebService;

import Logic.EmployeeLogic;
import Logic.IEmployeeResponse;
import Logic.IEmployeeRequest;
import Logic.EmployeeRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static WebService.Object2TextParser.*;
import static java.lang.Integer.parseInt;

@WebServlet("/api/employee")
public class EmployeeRoute extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rawPayload = "";

        if (request.getParameter("id") != null){
            IEmployeeResponse inStorageEmployee = new EmployeeLogic().get(parseInt(request.getParameter("id")));
            rawPayload = buildEmployee(request.getParameter("mode"), inStorageEmployee);
        } else {
            List<IEmployeeResponse> inStorageEmployees = new EmployeeLogic().get();
            if (inStorageEmployees.isEmpty())
                rawPayload= "<p>Nothing to show.</p>";
            else {
                for (IEmployeeResponse employee : inStorageEmployees) {
                    String tableRow = employeeTableRow(employee);

                    rawPayload = rawPayload.concat(tableRow);
                }
            }
        }

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        int departmentId = parseInt(request.getParameter("departmentId"));
        IEmployeeRequest employeeData =  new EmployeeRequest(
            request.getParameter("name"),
            request.getParameter("lastName"),
            request.getParameter("birthDate"),
            departmentId
        );

        IEmployeeResponse employee = null;
        if (request.getParameter("id") != null)
            employee = new EmployeeLogic().update(parseInt(request.getParameter("id")), employeeData);
        else
            employee = new EmployeeLogic().save(employeeData);

        String rawPayload = "<p>Request fail</p>";
        if (employee != null)
            rawPayload = employeeDefaultForm();

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newEmployee");
        response.getWriter().append(rawPayload);
    }


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rawPayload = "<p>The employee could not be deleted.</p>";

        if (request.getParameter("id") != null) {
            IEmployeeResponse deletedEmployee = new EmployeeLogic().delete(parseInt(request.getParameter("id")));
            if(deletedEmployee != null)
                rawPayload = "<p>The employee was deleted successfully!.</p>";
        }

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newEmployee");
        response.getWriter().append(rawPayload);
    }
}
