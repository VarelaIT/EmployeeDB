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
import java.util.Map;

import static WebService.Object2TextParser.*;
import static WebService.RequestSanitizer.getParams;
import static java.lang.Integer.parseInt;
import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@WebServlet("/api/department")
public class DepartmentRoute extends HttpServlet {

    private static final Logger logger = LogManager.getLogger("regular");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = getParams(request);
        String rawPayload = "<p>Department not found.<p>";
        String mode = params.getOrDefault("mode", "");
        Integer size = null;
        Integer page = null;
        Integer id = null;
        try {
            page = parseInt(params.getOrDefault("size",null));
            size = parseInt(params.getOrDefault("size",null));
            id = parseInt(params.getOrDefault("id", null));
        } catch (Exception e){
            logger.warn(
                "While parsing request parameters in "
                + DepartmentRoute.class
                + ":\n\t" + e.getMessage()
            );
        }

        if (id != null){
            IDepartmentResponse inStorageDepartment = new DepartmentLogic().get(id);
            if (inStorageDepartment != null)
                rawPayload = buildDepartment(mode, inStorageDepartment);
        } else {
            List<IDepartmentResponse> inStorageDepartments = new DepartmentLogic().get(page, size);
            if (inStorageDepartments != null) {
                rawPayload = "";
                for (IDepartmentResponse department : inStorageDepartments) {
                    String parsedDepartment = buildDepartment(mode, department);
                    rawPayload = rawPayload.concat(parsedDepartment);
                }
            }
        }

        rawPayload.concat(PagerComponent.department(size, page));

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = getParams(request);
        String department = params.getOrDefault("department", null);
        String description = params.getOrDefault("description", null);
        IDepartmentRequest departmentData = new DepartmentRequest(department, description);
        IDepartmentResponse departmentResponse = null;
        String rawPayload = "<p>Request fail.<p>";

        if (request.getParameter("id") != null)
            departmentResponse = new DepartmentLogic().update(parseInt(request.getParameter("id")), departmentData);
        else
            departmentResponse = new DepartmentLogic().save(departmentData);

        if (departmentResponse != null)
            rawPayload = departmentDefaultForm();

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newDepartment");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = getParams(request);
        String rawPayload = "<p>The department could not be deleted.</p>";
        Integer id = null;
        try {
            id = parseInt(params.getOrDefault("id", null));
        } catch (Exception e){
            logger.warn(
                "While parsing request parameters in "
                + DepartmentRoute.class
                + ":\n\t" + e.getMessage()
            );
        }

        if (id != null) {
            IDepartmentResponse deletedDepartment = new DepartmentLogic().delete(parseInt(request.getParameter("id")));
            if(deletedDepartment != null)
                rawPayload = departmentDefaultForm();
        }

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newDepartment");
        response.getWriter().append(rawPayload);
    }
}
