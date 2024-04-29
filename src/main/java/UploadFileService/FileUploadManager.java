package UploadFileService;

import Persistence.ITableNameBuilder;
import Persistence.TableNameBuilder;
import Persistence.UploadRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public class FileUploadManager implements IFileUploadManager{

    public final java.sql.Date today = new java.sql.Date(new java.util.Date().getTime());
    public final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final Logger logger = LogManager.getLogger("regular");
    private String test = null;
    private final String filePath;
    private final int processId;
    private ITableNameBuilder tableNames;

    public FileUploadManager(int processId, String filePath){
        this.processId = processId;
        this.filePath = filePath;
        manage();
    }

    public FileUploadManager(int processId, String filePath, String test){
        this.processId = processId;
        this.filePath = filePath;
        this.test = test;
        manage();
    }

    @Override
    public void manage(){
        tableNames = new TableNameBuilder(processId);
        int counter = 0;
        StringBuilder chunk = new StringBuilder();
        StringBuilder validChunk = new StringBuilder();
        StringBuilder invalidChunk = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                String validRegister = parseLine(line);
                counter++;

                if (validRegister != null) {
                    String validLine = "(" + counter + ")";
                    if (chunk.isEmpty()){
                        chunk.append(validRegister);
                        validChunk.append(validLine);
                    } else {
                        chunk.append(", ").append(validRegister);
                        validChunk.append(", ").append(validLine);
                    }

                } else {
                    String invalidLine = "(" + counter + ")";
                    if (invalidChunk.isEmpty())
                        invalidChunk.append(invalidLine);
                    else
                        invalidChunk.append(", ").append(invalidLine);
                }

                if (counter > 0 && counter % 1000 == 0){
                    saveProcessedLines(chunk.toString(), validChunk.toString(), invalidChunk.toString());
                    //clear chunk
                    chunk = new StringBuilder();
                    validChunk = new StringBuilder();
                    invalidChunk = new StringBuilder();
                }

            }
            //saves total lines processed
            new UploadRepository(test).updateTotalLines(processId, counter);
            //remaining chunk
            saveProcessedLines(chunk.toString(), validChunk.toString(), invalidChunk.toString());

        } catch (Exception e) {
            logger.error("While reading file:\n\t" + e.getMessage());
        }

    }


    public void saveProcessedLines ( String chunk, String validChunk, String invalidChunk) {
        //save chunk
        if (!chunk.isEmpty()) {
            Thread saveChunkThread = new Thread(new SaveEmployeeChunkThread(chunk, test));
            saveChunkThread.start();
        }
        //new thread to report valid lines
        if (!validChunk.isEmpty()) {
            Thread valildLineThread = new Thread(new ReportLinesThread(tableNames.succeed(), validChunk, test));
            valildLineThread.start();
        }
        //new thread to report invalid lines
        if (!invalidChunk.isEmpty()) {
            Thread failedLineThread = new Thread(new ReportLinesThread(tableNames.failed(), invalidChunk, test));
            failedLineThread.start();
        }
    }

    public String parseLine(String line){
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
