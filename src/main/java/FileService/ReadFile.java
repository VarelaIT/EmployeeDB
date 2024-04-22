package FileService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public class ReadFile {

    public static final java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());
    public static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = LogManager.getLogger("regular");

    public static boolean reader(String file){

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String validLine = parseLine(line);
                if (validLine != null)
                    System.out.println(validLine);
            }

            return true;
        } catch (Exception e) {
            logger.error("While reading file:\n\t" + e.getMessage());
            return false;
        }

    }

    public static String parseLine(String line){
        if (line != null){
            String[] fields = line.split("<|>|--|,");

            if (fields.length != 4)
                return null;

            try {
                Date bd = new Date(formatter.parse(fields[2]).getTime());
                if (today.getYear() - bd.getYear() < 18)
                    return null;

                int group = parseInt(fields[3]);
                if (group < 1)
                    return  null;

            }catch (Exception e){
                logger.trace("While parsing file lines, invalid format.");
                return null;
            }

            return "(" + fields[0] + "," + fields[1] + "," + fields[2] + "," + fields[3] + ")";
        }

        return null;
    }
}
