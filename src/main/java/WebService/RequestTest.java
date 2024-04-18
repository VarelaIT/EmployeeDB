package WebService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

@WebServlet("/api/request")
public class RequestTest extends HttpServlet {

    public String getAnswer(Map<String, String[]> params){
        String answer = "";

        for (String key : params.keySet()) {
            answer.concat(key + " => " + String.join(", ", params.get(key)));
        }

        return answer;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //Map<String, String[]> params = request.getParameterMap();
        //String answer = getAnswer(params);
        response.setContentType("text/html");

        if (request.getParameter("name") == null && request.getParameter("lastName") == null)
            response.getWriter().append("<p>The request is invalid</p>");
        else {

            String answer = "name = " + escapeHtml4(request.getParameter("name"))
                    + "lastName = " + escapeHtml4(request.getParameter("lastName"));

            response.getWriter().append("<h1>Hello world</h1>").append(answer);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();

        String answer = getAnswer(params);

        response.setContentType("text/html");
        response.getWriter().append(answer);
    }
}
