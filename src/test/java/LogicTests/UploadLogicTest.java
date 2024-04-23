package LogicTests;

import Logic.IUploadLogic;
import Logic.IUploadStatusResponse;
import Logic.UploadLogic;
import Persistence.IUploadStatus;
import Persistence.TableSchemas;
import Persistence.UploadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UploadLogicTest {

    private IUploadLogic uploadLogic = null;
    private final String test = "test";
    @BeforeEach
    public void employeeStorageInitialization(){
        TableSchemas.dropUploadsTable(test);
        TableSchemas.createUploadsTable(test);
        TableSchemas.dropFailedLinesTable(test);
        TableSchemas.createFailedLinesTable(test);
        TableSchemas.dropEmployeesTable(test);
        TableSchemas.createEmployeesTable(test);
        uploadLogic = new UploadLogic(test);
    }

    @Test
    public void getUploadStatus() throws InterruptedException {
        String fileName = "logicFile";
        String location = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        Integer processId = uploadLogic.start(fileName, location);
        Thread.sleep(2000);

        IUploadStatusResponse status = uploadLogic.status(processId);
        System.out.println("Total lines: " + status.getTotal() + "\nSucceed: " + status.getCompleted() + "\nFailed: " + status.getFailed());

        assertNotNull(status);
        assertEquals(processId, status.getProcessId());
        assertEquals(fileName, status.getFileName());
        assertEquals(status.getTotal(), status.getCompleted() + status.getFailed());

    }

    @Test
    public void startUpload() throws InterruptedException {
        String fileName = "logicFile";
        String location = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";

        Integer processId = uploadLogic.start(fileName, location);
        Thread.sleep(2000);
        IUploadStatus status = new UploadRepository(test).getStatus(processId);
        System.out.println("Total lines: " + status.getTotal() + "\nSucceed: " + status.getCompleted() + "\nFailed: " + status.getFailed());

        assertNotNull(status);
        assertEquals(status.getTotal(), status.getCompleted() + status.getFailed());
    }

}
