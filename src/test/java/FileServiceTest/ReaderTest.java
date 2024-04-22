package FileServiceTest;

import FileService.ReadFile;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {

    @Test
    public void readValidFile(){
        //String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesBadLines.csv";

        boolean result = ReadFile.reader(filePath);

        assertTrue(result);
    }
}
