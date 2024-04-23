package FileServiceTest;

import UploadFileService.ReadFile;
import Persistence.TableSchemas;
import Persistence.UploadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {


    @BeforeEach
    public void employeeStorageInitialization(){
        TableSchemas.dropUploadsTable("test");
        TableSchemas.createUploadsTable("test");
        TableSchemas.dropEmployeesTable("test");
        TableSchemas.createEmployeesTable("test");
    }

    @Test
    public void readValidFile() throws InterruptedException {
        //String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        Integer processId = new UploadRepository("test").create("someFile");

        ReadFile.manage(processId, filePath, "test");

        Thread.sleep(2000);
        assertTrue(processId == 1);
    }
}
