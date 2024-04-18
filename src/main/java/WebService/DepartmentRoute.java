package WebService;

import Entities.IPersistedDepartment;
import Logic.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static WebService.Object2TextParser.*;
import static java.lang.Integer.parseInt;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@WebServlet("/api/department")
public class DepartmentRoute extends HttpServlet {

    //private static final Logger logger = LogManager.getLogger("regular");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mode = request.getParameter("mode") != null? request.getParameter("mode") : "";
        String rawPayload = "<p>Department not found.<p>";

        if (request.getParameter("id") != null){
            int id =parseInt( escapeHtml4(request.getParameter("id")));
            IDepartmentResponse inStorageDepartment = new DepartmentLogic().get(id);
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
        response.setContentType("text/html");

            response.getWriter().append("<p>The request is invalid</p>");
        else {

            String department = escapeHtml4(request.getParameter("department"));
            String description = escapeHtml4(request.getParameter("description"));

            IDepartmentRequest departmentData = new DepartmentRequest(department, description);

            IDepartmentResponse departmentResponse = null;
            if (request.getParameter("id") != null)
                departmentResponse = new DepartmentLogic().update(parseInt(request.getParameter("id")), departmentData);
            else
                departmentResponse = new DepartmentLogic().save(departmentData);

            String rawPayload = "<p>Request fail.<p>";
            if (departmentResponse != null)
                rawPayload = departmentDefaultForm();

            response.addHeader("HX-Trigger", "newDepartment");
            response.getWriter().append(rawPayload);
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String rawPayload = "<p>The department could not be deleted.</p>";

        if (request.getParameter("id") != null) {
            IDepartmentResponse deletedDepartment = new DepartmentLogic().delete(parseInt(request.getParameter("id")));
            if(deletedDepartment != null)
                rawPayload = departmentDefaultForm();
        }

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newDepartment");
        response.getWriter().append(rawPayload);
    }
}
