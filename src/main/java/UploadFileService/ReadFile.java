package UploadFileService;

import Persistence.UploadRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public class ReadFile {

    public static final java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());
    public static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = LogManager.getLogger("regular");

    public static void manage(int processId, String filePath, String test){
        int counter = 0;
        StringBuilder chunk = new StringBuilder();
        StringBuilder invalidChunk = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String validLine = parseLine(line);
                counter++;

                if (validLine != null) {
                    if (chunk.isEmpty())
                        chunk.append(validLine);
                    else
                        chunk.append(", ").append(validLine);
                } else {
                    String invalidLine = "(" + processId + ", " + counter + ")";
                    if (invalidChunk.isEmpty())
                        invalidChunk.append(invalidLine);
                    else
                        invalidChunk.append(", ").append(invalidLine);
                }

                if (counter > 0 && counter % 1000 == 0){
                    //new thread to report invalid line
                    if (!invalidChunk.isEmpty()) {
                        Thread failedLineThread = new Thread(new ReportFailedThread(processId, invalidChunk.toString(), test));
                        failedLineThread.start();
                    }
                    //save chunk
                    if (!chunk.isEmpty()) {
                        Thread saveChunkThread = new Thread(new SaveChunkThread(processId, chunk.toString(), test));
                        saveChunkThread.start();
                    }

                    //clear chunk
                    chunk = new StringBuilder();
                    invalidChunk = new StringBuilder();
                }

            }
            //remaining chunk
            if (!chunk.isEmpty()) {
                Thread saveChunkThread = new Thread(new SaveChunkThread(processId, chunk.toString(), test));
                saveChunkThread.start();
            }

            if (!invalidChunk.isEmpty()) {
                Thread failedLineThread = new Thread(new ReportFailedThread(processId, invalidChunk.toString(), test));
                failedLineThread.start();
            }
            //saves total lines processed
            new UploadRepository(test).updateTotalLines(processId, counter);

        } catch (Exception e) {
            logger.error("While reading file:\n\t" + e.getMessage());
        }

    }

    public static String parseLine(String line){
        if (line != null){
            String[] fields = line.split("<|>|--|,");

            if (fields.length != 4)
                return null;

            if (fields[0].length() > 64 || fields[1].length() > 64)
                return  null;

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

            return "('" + fields[0] + "', '" + fields[1] + "', '" + fields[2] + "', " + fields[3] + ")";
        }

        return null;
    }
}
