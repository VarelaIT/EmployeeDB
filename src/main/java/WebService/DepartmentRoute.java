package WebService;

import Logic.DepartmentLogic;
import Logic.DepartmentRequest;
import Logic.IDepartmentRequest;
import jakarta.servlet.ServletInputStream;
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

        String rawPayload = new DepartmentLogic().get();

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDepartmentRequest createDepartment = new DepartmentRequest(
            request.getParameter("department"),
            request.getParameter("description")
        );

        String rawPayload = new DepartmentLogic().save(createDepartment);

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

}
