package WebService;

import Entities.IPersistedDepartment;
import Logic.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static WebService.Object2TextParser.*;
import static java.lang.Integer.parseInt;

@WebServlet("/api/department")
public class DepartmentRoute extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mode = request.getParameter("mode") != null? request.getParameter("mode") : "";
        String rawPayload = "<p>Department not found.<p>";

        if (request.getParameter("id") != null){
            IDepartmentResponse inStorageDepartment = new DepartmentLogic().get(parseInt(request.getParameter("id")));
            if (inStorageDepartment != null)
                rawPayload = buildDepartment(mode, inStorageDepartment);
        } else {
            List<IDepartmentResponse> inStorageDepartments = new DepartmentLogic().get();
            if (inStorageDepartments != null) {
                rawPayload = "";
                for (IDepartmentResponse department : inStorageDepartments) {
                    String parsedDepartment = buildDepartment(mode, department);
                    rawPayload = rawPayload.concat(parsedDepartment);
                }
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

        String rawPayload = "<p>Request fail.<p>";
        if (department != null)
            rawPayload = departmentDefaultForm();

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
