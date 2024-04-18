package WebService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class ValidateDepartmentInput {
    public static Map<String, String> validateDepartmentRequest(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        Map<String, String> params = null;

       if (request.getParameter("department") != null && request.getParameter("description") != null){
           params.put("department", escapeHtml4(request.getParameter("department")));
           params.put("description", escapeHtml4(request.getParameter("description")));
       }


       return null;
    }
}
