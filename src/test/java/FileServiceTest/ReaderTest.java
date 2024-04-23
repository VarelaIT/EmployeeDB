package FileServiceTest;

import Persistence.IUploadRepository;
import Persistence.IUploadStatus;
import UploadFileService.ManagerThread;
import UploadFileService.ReadFile;
import Persistence.TableSchemas;
import Persistence.UploadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {


    @BeforeEach
    public void employeeStorageInitialization(){
        TableSchemas.dropUploadsTable("test");
        TableSchemas.createUploadsTable("test");
        TableSchemas.dropFailedLinesTable("test");
        TableSchemas.createFailedLinesTable("test");
        TableSchemas.dropEmployeesTable("test");
        TableSchemas.createEmployeesTable("test");
    }

    @Test
    public void readValidFile() throws InterruptedException {
        //String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        IUploadRepository uploadRepository = new UploadRepository("test");
        Integer processId = uploadRepository.create("someFile");

        ReadFile.manage(processId, filePath, "test");
        Thread.sleep(2000);
        IUploadStatus status = uploadRepository.getStatus(processId);
        System.out.println("Total lines: " + status.getTotal() + "\nSucceed: " + status.getCompleted() + "\nFailed: " + status.getFailed());

        assertNotNull(status);
        assertEquals(status.getTotal(), status.getCompleted() + status.getFailed());
    }

    @Test
    public void readValidFileManagerThread() throws InterruptedException {
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        //String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        IUploadRepository uploadRepository = new UploadRepository("test");
        Integer processId = uploadRepository.create("someFile");

        Thread fileProcess = new Thread (new ManagerThread(processId, filePath, "test"));
        fileProcess.start();
        Thread.sleep(2000);
        IUploadStatus status = uploadRepository.getStatus(processId);
        System.out.println("Total lines: " + status.getTotal() + "\nSucceed: " + status.getCompleted() + "\nFailed: " + status.getFailed());

        assertNotNull(status);
        assertEquals(status.getTotal(), status.getCompleted() + status.getFailed());
    }
}
