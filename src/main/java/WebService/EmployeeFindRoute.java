package WebService;

import Logic.EmployeeLogic;
import Logic.IEmployeeResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static WebService.Object2TextParser.employeeTableRow;
import static WebService.RequestSanitizer.getParams;

@WebServlet("/api/employee/find")
public class EmployeeFindRoute extends HttpServlet{

    //private static final Logger logger = LogManager.getLogger("regular");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = getParams(request);
        String pattern = params.getOrDefault("pattern", null);
        String rawPayload= "<p>Pattern " + pattern + " not found.</p>";

        if (pattern != null) {
            List<IEmployeeResponse> inStorageEmployees = new EmployeeLogic().find(pattern);
            if (inStorageEmployees != null && !inStorageEmployees.isEmpty()) {
                rawPayload = "";
                for (IEmployeeResponse employee : inStorageEmployees) {
                    String tableRow = employeeTableRow(employee);
                    rawPayload = rawPayload.concat(tableRow);
                }
            }
        }

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }
}
