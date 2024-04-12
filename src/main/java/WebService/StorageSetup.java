package WebService;

import Logic.Setup;
import Logic.IStorageSetup;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/setup")
public class StorageSetup extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        IStorageSetup manager = new Setup();
        boolean signal = manager.formatStorage();

        if (signal)
            response.getWriter().append("<p>The Storage was formatted successfully.</p>");
        else
            response.getWriter().append("<p>The Storage was Not formatted. Please, try again</p>");
    }

}
