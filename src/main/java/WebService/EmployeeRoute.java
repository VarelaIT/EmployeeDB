package WebService;

import Logic.EmployeeLogic;
import Logic.IEmployeeResponse;
import Logic.IEmployeeRequest;
import Logic.EmployeeRequest;
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

@WebServlet("/api/employee")
public class EmployeeRoute extends HttpServlet {

    private static final Logger logger = LogManager.getLogger("regular");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = getParams(request);
        String rawPayload= "<p>Nothing to show.</p>";
        String mode = params.getOrDefault("mode", "");
        Integer size = null;
        Integer page = null;
        Integer id = null;
        try {
            page = parseInt(params.getOrDefault("page","0"));
            size = parseInt(params.getOrDefault("size","25"));
            id = parseInt(params.getOrDefault("id", null));
        } catch (Exception e){
            logger.warn("While parsing request parameters in " + DepartmentRoute.class + ":\n\t" + e.getMessage());
        }

        if (id != null){
            IEmployeeResponse inStorageEmployee = new EmployeeLogic().get(id);
            rawPayload = buildEmployee(mode, inStorageEmployee);
        } else {
            List<IEmployeeResponse> inStorageEmployees = new EmployeeLogic().get(size, page);
            if (inStorageEmployees != null){
                rawPayload = "";
                for (IEmployeeResponse employee : inStorageEmployees) {
                    String tableRow = employeeTableRow(employee);
                    rawPayload = rawPayload.concat(tableRow);
                }

                if (mode.isEmpty())
                    rawPayload = rawPayload.concat(PagerComponent.build("employee", size, page));
            }
        }

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        Map<String, String> params = getParams(request);
        Integer departmentId = null;
        try {
            departmentId = parseInt(params.getOrDefault("departmentId", null));
        } catch (Exception e) {
            logger.warn("While parsing request parameters in " + DepartmentRoute.class + ":\n\t" + e.getMessage());
        }
        IEmployeeRequest employeeData =  new EmployeeRequest(
            params.getOrDefault("name", null),
            params.getOrDefault("lastName", null),
            params.getOrDefault("birthDate", null),
            departmentId
        );
        IEmployeeResponse employee = null;
        String rawPayload = "<p>Request fail</p>";

        if (params.containsKey("id")) {
            Integer id = null;
            try {
                id = parseInt(params.getOrDefault("id", null));
            } catch (Exception e) {
                logger.warn("While parsing request parameters in " + DepartmentRoute.class + ":\n\t" + e.getMessage());
            }
            employee = new EmployeeLogic().update(id, employeeData);
        }else
            employee = new EmployeeLogic().save(employeeData);

        if (employee != null)
            rawPayload = employeeDefaultForm();

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newEmployee");
        response.getWriter().append(rawPayload);
    }


    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = getParams(request);
        String rawPayload = "<p>The employee could not be deleted. Try again.</p>";
        Integer id = null;
        try {
            id = parseInt(params.getOrDefault("id", null));
        } catch (Exception e) {
            logger.warn("While parsing request parameters in " + DepartmentRoute.class + ":\n\t" + e.getMessage());
        }

        if (id != null) {
            IEmployeeResponse deletedEmployee = new EmployeeLogic().delete(id);

            if (deletedEmployee != null)
                rawPayload = employeeDefaultForm();
        }

        response.setContentType("text/html");
        response.addHeader("HX-Trigger", "newEmployee");
        response.getWriter().append(rawPayload);
    }
}
