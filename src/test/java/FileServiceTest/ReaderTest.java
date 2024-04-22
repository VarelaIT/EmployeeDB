package FileServiceTest;

import FileService.ReadFile;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderTest {

    @Test
    public void readValidFile(){
        String filePath = "/home/uriel/www/EmployeeDB/src/main/webapp/uploads/employeesGPT.csv";

        boolean result = ReadFile.read(filePath);

        assertTrue(result);
    }
}
