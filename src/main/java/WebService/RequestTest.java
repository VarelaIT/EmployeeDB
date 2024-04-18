package WebService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static WebService.RequestSanitizer.getParams;
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
        Map<String, String> params = getParams(request);
        StringBuilder answer = new StringBuilder();
        answer.append("<h3>Parameters quantity ").append(params.size()).append("</h3>");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String payload =
                "<p>"
                + entry.getKey()
                + ": "
                + entry.getValue()
                + "</p>";

            answer.append(payload);
        }

        response.setContentType("text/html");
        response.getWriter().append("<h1>Testing the requests</h1>").append(answer);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String[]> params = request.getParameterMap();

        String answer = getAnswer(params);

        response.setContentType("text/html");
        response.getWriter().append(answer);
    }
}
