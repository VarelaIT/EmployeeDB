package WebService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class RequestSanitizer {
    public static Map<String, String> getParams (HttpServletRequest request){
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<>();

        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            StringBuilder paramValue = new StringBuilder();

            for (String value : paramValues) {
                paramValue.append(value);
            }

            params.put(escapeHtml4(paramName), escapeHtml4(paramValue.toString()));
        }


       return params;
    }
}
