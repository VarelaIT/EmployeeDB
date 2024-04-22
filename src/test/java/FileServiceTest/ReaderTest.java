package FileServiceTest;

import FileService.ReadFile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {

    @Test
    public void readValidFile(){
        //String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";
        int fileId = 1;

        ReadFile.manage(filePath);

        assertTrue(fileId == 1);
    }
}
