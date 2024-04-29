package FileServiceTest;

import Persistence.*;
import UploadFileService.IFileUploadManager;
import UploadFileService.ManagerThread;
import UploadFileService.FileUploadManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UploadManagerTest {

    private final String test = "test";

    @BeforeEach
    public void employeeStorageInitialization(){
        TableSchemas.dropUploadsTable(test);
        TableSchemas.createUploadsTable(test);
        TableSchemas.dropFailedLinesTable(test);
        TableSchemas.createFailedLinesTable(test);
        TableSchemas.dropEmployeesTable(test);
        TableSchemas.createEmployeesTable(test);
    }

    @Test
    public void processValidFileUpload() throws InterruptedException {
        String filePathA = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        IUploadRepository uploadRepository = new UploadRepository(test);
        Integer processId = uploadRepository.create("someFileA");
        ITableNameBuilder tableName = new TableNameBuilder(processId);
        TableSchemas.createTemporaryLinesTable(test, tableName.succeed());
        TableSchemas.createTemporaryLinesTable(test, tableName.failed());

        new FileUploadManager(processId, filePathA, test);
        Thread.sleep(1000);
        Integer succeed = uploadRepository.countLines(tableName.succeed());
        Integer failed = uploadRepository.countLines(tableName.failed());

        assertEquals(89, succeed);
        assertEquals(0, failed);
        TableSchemas.dropTemporaryLinesTable(test, tableName.succeed());
        TableSchemas.dropTemporaryLinesTable(test, tableName.failed());

    }

    /*
    @Test
    public void readManyFiles() throws InterruptedException {
        String filePathA = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        String filePathB = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        IUploadRepository uploadRepository = new UploadRepository(test);
        Integer processIdA = uploadRepository.create("someFileA");
        Integer processIdB = uploadRepository.create("someFileB");

        FileUploadManager.manage(processIdA, filePathA, test);
        FileUploadManager.manage(processIdB, filePathB, test);
        Thread.sleep(2000);
        IUploadStatus statusA = uploadRepository.getStatus(processIdA);
        IUploadStatus statusB = uploadRepository.getStatus(processIdB);
        System.out.println("Total lines: " + statusA.getTotal() + "\nSucceed: " + statusA.getCompleted() + "\nFailed: " + statusA.getFailed());

        assertNotNull(statusA);
        assertNotNull(statusB);
        assertNotEquals(processIdA, processIdB);
        assertNotEquals(statusA.getFailed(), statusB.getFailed());
        assertEquals(statusA.getTotal(), statusA.getCompleted() + statusA.getFailed());
        assertEquals(statusB.getTotal(), statusB.getCompleted() + statusB.getFailed());
    }

    @Test
    public void readValidFile() throws InterruptedException {
        //String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        IUploadRepository uploadRepository = new UploadRepository(test);
        Integer processId = uploadRepository.create("someFile");

        FileUploadManager.manage(processId, filePath, test);
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
        IUploadRepository uploadRepository = new UploadRepository(test);
        Integer processId = uploadRepository.create("someFile");

        Thread fileProcess = new Thread (new ManagerThread(processId, filePath, test));
        fileProcess.start();
        Thread.sleep(2000);
        IUploadStatus status = uploadRepository.getStatus(processId);
        System.out.println("Total lines: " + status.getTotal() + "\nSucceed: " + status.getCompleted() + "\nFailed: " + status.getFailed());

        assertNotNull(status);
        assertEquals(status.getTotal(), status.getCompleted() + status.getFailed());
    }

     */
}
