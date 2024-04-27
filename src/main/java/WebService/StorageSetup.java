package WebService;

import Logic.Setup;
import Logic.IStorageSetup;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/api/setup")
public class StorageSetup extends HttpServlet{

    private static final Logger logger = LogManager.getLogger("regular");

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        IStorageSetup manager = new Setup();
        boolean signal = manager.formatStorage();

        if (signal) {
            logger.trace("The storage was formatted successfully.");

            if (manager.defaultDepartments())
                response.getWriter().append("<p>The Storage was formatted successfully.</p>");
            else
                response.getWriter().append("<p>The Storage was formatted, but no default department were loaded.</p>");
        } else {
            logger.error("The storage was not formatted as expected.");
            response.getWriter().append("<p>The Storage was Not formatted. Please, try again</p>");
        }
    }

}
