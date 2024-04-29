package FileServiceTest;

import Persistence.*;
import UploadFileService.IFileUploadManager;
import UploadFileService.ManagerThread;
import UploadFileService.FileUploadManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UploadManagerTest {

    private IUploadRepository uploadRepository;
    private final String test = "test";

    @BeforeEach
    public void employeeStorageInitialization(){
        TableSchemas.dropUploadsTable(test);
        TableSchemas.createUploadsTable(test);
        TableSchemas.dropFailedLinesTable(test);
        TableSchemas.createFailedLinesTable(test);
        TableSchemas.dropEmployeesTable(test);
        TableSchemas.createEmployeesTable(test);
        uploadRepository = new UploadRepository(test);
    }

    @Test
    public void processValidFileUpload() throws InterruptedException {
        String filePathA = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        Integer processId = uploadRepository.create("someFile");
        ITableNameBuilder tableName = new TableNameBuilder(processId);
        TableSchemas.dropTemporaryLinesTable(test, tableName.succeed());
        TableSchemas.dropTemporaryLinesTable(test, tableName.failed());
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

}
