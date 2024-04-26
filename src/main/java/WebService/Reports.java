package WebService;

import Logic.EmployeeLogic;
import Logic.IReportEmployeesPerDepartmentResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static WebService.RequestSanitizer.getParams;

@WebServlet("/api/reports")
public class Reports extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> params = getParams(request);
        String rawPayload = "<p>Not found.</p>";

        rawPayload =  getReport(params.getOrDefault("chart", rawPayload));

        response.setContentType("text/html");
        response.getWriter().append(rawPayload);
    }

    public String chartEmployeesPerDepartment() {
        List<IReportEmployeesPerDepartmentResponse> report = new EmployeeLogic().reportEmployeesPerDepartment();

        if (report == null)
            return "<p>Empty report.</p>";

        String dataPoints = "";
        for (int i = 0; i < report.size(); i ++){
            dataPoints = dataPoints + "{ label: \"" + report.get(i).getDepartment() + "\",  y: " + report.get(i).getTotal() + "  },";
        }

        String chart = """
            <script type="text/javascript">
                function employeesPerDepartmentChart () {
                                    
                    const chart = new CanvasJS.Chart("chartContainer", {
                        theme: "light1", // "light2", "dark1", "dark2"
                        animationEnabled: true, // change to true
                        title:{
                            text: "Employees per Department"
                        },
                        data: [
                        {
                            // Change type to "bar", "area", "spline", "pie",etc.
                            type: "column",
                            dataPoints: [
                                $dataPoints
                            ]
                        }
                        ]
                    });
                    chart.render();
                                
                }
                
                employeesPerDepartmentChart();
            </script>
        """;
        return chart.replace("$dataPoints", dataPoints);
    }

    public String getReport(String petition) {

        if (petition.equals("c1"))
            return chartEmployeesPerDepartment();

       return petition;
    }

}
