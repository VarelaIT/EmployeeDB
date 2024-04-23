package WebService;

import Logic.IUploadLogic;
import Logic.UploadLogic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet("/api/employee/upload")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 10   // 10 MB
)
public class EmployeeFileRoute extends HttpServlet{

    private final String UPLOAD_DIR = "uploads";

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String payload = "file uploaded.";
        response.setContentType("text/html");
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();

        String applicationPath = getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (fileName == null || fileName.isEmpty())
            payload = "No file selected to upload!";
        else {
            String fileFullPath = uploadFilePath + File.separator + fileName;
            for (Part part : request.getParts()) {
                part.write(fileFullPath);
            }

            IUploadLogic uploadLogic = new UploadLogic();
            Integer processId = uploadLogic.start(fileName, fileFullPath);

            payload = uploadStatusComponent(processId, "start");
        }

        response.getWriter().append(payload);
    }

    private String uploadStatusComponent(Integer processId, String start) {
        String body = """
            <table>
                <thead>
                    <tr>
                        <th>Processed registers</th>
                        <th>Valid</th>
                        <th>Invalid</th>
                        <th>Total</th>
                    </tr>
                <thead>
                <tbody>
                    <tr
                        hx-get='/EmployeeDB/api/employee/upload/status/?id=$processId'
                        hx-trigger='load delay:0.5s'
                        hx-swap='outerHTML'
                    ></tr>
                <tbody>
            </table>
        """;

        return body.replace("$processId", "" + processId);
    }
}
