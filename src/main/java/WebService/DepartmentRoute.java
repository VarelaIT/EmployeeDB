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

        String rawPayload = "";

        if (request.getParameter("id") != null){
            IDepartmentResponse inStorageDepartment = new DepartmentLogic().get(parseInt(request.getParameter("id")));
            rawPayload = new Object2TextParser().buildDepartment(request.getParameter("mode"), inStorageDepartment);
        } else {
            List<IDepartmentResponse> inStorageDepartments = new DepartmentLogic().get();
            for (IPersistedDepartment department : inStorageDepartments) {
                String tableRow = new Object2TextParser().departmentTableRow(department);

                rawPayload = rawPayload.concat(tableRow);
            }
        }

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDepartmentRequest department2Create = new DepartmentRequest(
                request.getParameter("department"),
                request.getParameter("description")
        );

        IDepartmentResponse department = new DepartmentLogic().save(department2Create);
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

        int id = parseInt(request.getParameter("id"));

        IDepartmentResponse department = new DepartmentLogic().update(id, department2Update);
        String rawPayload = new Object2TextParser().departmentTableRow(department);

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

}
