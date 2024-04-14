package WebService;

import Logic.EmployeeLogic;
import Logic.IEmployeeResponse;
import Logic.IEmployeeRequest;
import Logic.EmployeeRequest;
import Persistence.IPersistedEmployee;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static java.lang.Integer.parseInt;

@WebServlet("/api/employee")
public class EmployeeRoute extends HttpServlet {

    private DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        int departmentId = parseInt(request.getParameter("departmentId"));
        String birthDate = request.getParameter("birthDate");
        IEmployeeRequest employeeData = null;
        try {
            employeeData = new EmployeeRequest(
                    request.getParameter("name"),
                    request.getParameter("lastName"),
                    birthDate,
                    departmentId
            );
        } catch (ParseException e) {
            throw new RuntimeException("Invalid format while trying to store employee.");
        }

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
