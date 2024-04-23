package FileServiceTest;

import FileService.ReadFile;
import Persistence.EmployeeRepository;
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
    }

    @Test
    public void readValidFile() throws InterruptedException {
        //String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        Integer processId = new UploadRepository("test").create("someFile");

        ReadFile.manage(processId, filePath);

        Thread.sleep(2000);
        assertTrue(processId == 1);
    }
}
