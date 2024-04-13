package WebService;

import Entities.IPersistedDepartment;
import Logic.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/api/department")
public class DepartmentRoute extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<IDepartmentResponse> inStorageDepartments = new DepartmentLogic().get();

        String rawPayload = "";

        for (IPersistedDepartment department : inStorageDepartments) {
            String tableRow = new Object2TextParser().departmentTableRow(department);

            rawPayload = rawPayload.concat(tableRow);
        }
        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDepartmentRequest createDepartment = new DepartmentRequest(
            request.getParameter("department"),
            request.getParameter("description")
        );

        IDepartmentResponse department = new DepartmentLogic().save(createDepartment);
        String rawPayload = new Object2TextParser().departmentTableRow(department);

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

}
