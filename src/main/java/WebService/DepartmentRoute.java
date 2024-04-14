package WebService;

import Entities.IPersistedDepartment;
import Logic.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

@WebServlet("/api/department")
public class DepartmentRoute extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mode = request.getParameter("mode");
        String rawPayload = "";

        if (request.getParameter("id") != null){
            IDepartmentResponse inStorageDepartment = new DepartmentLogic().get(parseInt(request.getParameter("id")));
            rawPayload = new Object2TextParser().buildDepartment(mode, inStorageDepartment);
        } else {
            List<IDepartmentResponse> inStorageDepartments = new DepartmentLogic().get();
            for (IDepartmentResponse department : inStorageDepartments) {
                String parsedDepartment = new Object2TextParser().buildDepartment(mode, department);

                rawPayload = rawPayload.concat(parsedDepartment);
            }
        }

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDepartmentRequest departmentData = new DepartmentRequest(
                request.getParameter("department"),
                request.getParameter("description")
        );

        IDepartmentResponse department = null;
        if (request.getParameter("id") != null)
            department = new DepartmentLogic().update(parseInt(request.getParameter("id")), departmentData);
        else
            department = new DepartmentLogic().save(departmentData);

        String rawPayload = new Object2TextParser().departmentTableRow(department);
        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newDepartment");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDepartmentRequest department2Update = new DepartmentRequest(
            request.getParameter("department"),
            request.getParameter("description")
        );
        IDepartmentResponse updatedDepartment = null;
        if (request.getParameter("id") != null)
            updatedDepartment= new DepartmentLogic().update(parseInt(request.getParameter("id")), department2Update);

        String rawPayload;
        if (updatedDepartment != null)
            rawPayload = new Object2TextParser().departmentDefaultForm();
        else
            rawPayload = "<p>The department was not updated</p>";

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newDepartment");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rawPayload = "<p>The department could not be deleted.</p>";

        if (request.getParameter("id") != null) {
            IDepartmentResponse deletedDepartment = new DepartmentLogic().delete(parseInt(request.getParameter("id")));
            if(deletedDepartment != null)
                rawPayload = "<p>The department was deleted successfully!.</p>";
        }

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newDepartment");
        response.getWriter().append(rawPayload);
    }
}
