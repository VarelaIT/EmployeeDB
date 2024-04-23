package WebService;

import Logic.IUploadLogic;
import Logic.IUploadStatusResponse;
import Logic.UploadLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static WebService.RequestSanitizer.getParams;
import static java.lang.Integer.parseInt;

@WebServlet("/api/employee/upload/status")
public class EmployeeUploadStatus extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String, String> params = getParams(request);
        String id = params.getOrDefault("id", null);
        Integer processId = parseInt(id);
        String payload = "<p>Process not found.</p>";

        if (processId > 0) {
            IUploadLogic uploadLogic = new UploadLogic();
            IUploadStatusResponse status = uploadLogic.status(processId);
            if (status != null){
                payload = renderStatus(status);
            }
        }

        response.setContentType("text/html");
        response.getWriter().append(payload);
    }

    private String renderStatus(IUploadStatusResponse status) {
        String component = "";

        if (status.getCompleted() + status.getFailed() == status.getTotal())
            component= component + "<tr>";
        else {
            component = component + "<tr hx-get='/EmployeeDB/api/employee/upload/status/?id="
                    + status.getProcessId()
                    + "'hx-trigger='load delay:0.5s' hx-swap='outerHTML'>";
        }

        component = component
                + "<td></td><td>" + status.getCompleted()+ "</td>"
                + "<td></td><td>" + status.getFailed()+ "</td>"
                + "<td></td><td>" + status.getTotal()+ "</td></tr>";

        return component;
    }


}
