package WebService;

import Entities.IPersistedDepartment;
import Persistence.DepartmentRepository;
import Persistence.IDepartmentRepository;
import Persistence.JDBC.DBConn;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/api/department")
public class DepartmentRoute extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDepartmentRepository departmentRepository = new DepartmentRepository();

        List<IPersistedDepartment> inStorageDepartments = departmentRepository.getAll();
        String rawPayload = "";

        for (IPersistedDepartment department : inStorageDepartments) {
            String tableRow = "<tr><td>" + department.getName() + "</td><td>"
                    + department.getDescription() + "</td><td>"
                    + department.getId() + "</td></tr>";

            rawPayload = rawPayload.concat(tableRow);
        }

        response.setContentType("text/plain");
        response.getWriter().println(rawPayload);
    }

}
