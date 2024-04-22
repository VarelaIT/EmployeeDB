package FileService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReadFile {

    private static final Logger logger = LogManager.getLogger("regular");

    public static boolean read(String file){

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null)
                System.out.println(st);

            return true;
        } catch (Exception e) {
            logger.error("While reading file:\n\t" + e.getMessage());
            return false;
        }

    }
}
