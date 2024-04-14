package WebService;

import Logic.EmployeeLogic;
import Logic.IEmployeeResponse;
import Persistence.IPersistedEmployee;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

@WebServlet("/api/employee")
public class EmployeeRoute extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rawPayload = "";

        if (request.getParameter("id") != null){
            IEmployeeResponse inStorageEmployee = new EmployeeLogic().get(parseInt(request.getParameter("id")));
            rawPayload = new Object2TextParser().buildEmployee(request.getParameter("mode"), inStorageEmployee);
        } else {
            List<IEmployeeResponse> inStorageEmployees = new EmployeeLogic().get();
            for (IPersistedEmployee employee : inStorageEmployees) {
                String tableRow = new Object2TextParser().employeeTableRow(employee);

                rawPayload = rawPayload.concat(tableRow);
            }
        }

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IEmployeeRequest employeeData = new EmployeeRequest(
                request.getParameter("employee"),
                request.getParameter("description")
        );

        IEmployeeResponse employee = null;
        if (request.getParameter("id") != null)
            employee = new EmployeeLogic().update(parseInt(request.getParameter("id")), employeeData);
        else
            employee = new EmployeeLogic().save(employeeData);

        String rawPayload = new Object2TextParser().employeeTableRow(employee);
        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newEmployee");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IEmployeeRequest employee2Update = new EmployeeRequest(
            request.getParameter("employee"),
            request.getParameter("description")
        );
        IEmployeeResponse updatedEmployee = null;
        if (request.getParameter("id") != null)
            updatedEmployee= new EmployeeLogic().update(parseInt(request.getParameter("id")), employee2Update);

        String rawPayload;
        if (updatedEmployee != null)
            rawPayload = new Object2TextParser().employeeDefaultForm();
        else
            rawPayload = "<p>The employee was not updated</p>";

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
