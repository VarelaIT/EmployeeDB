package PersistenceTests;

import Persistence.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UploadRepositoryTest {

    private IUploadRepository uploadRepository;
    private ITableNameBuilder tableName;
    private Integer processId;
    private String test = "test";

    @BeforeEach
    public void storageInitialization(){
        String fileName = "employees.csv";
        TableSchemas.createUploadsTable(test);
        uploadRepository = new UploadRepository(test);
        processId = uploadRepository.create(fileName);
        tableName = new TableNameBuilder(processId);
        TableSchemas.createTemporaryLinesTable(test, tableName.succeed());
        TableSchemas.createTemporaryLinesTable(test, tableName.failed());
    }

    @AfterEach
    public void destroyStorage(){
        TableSchemas.dropTemporaryLinesTable(test, tableName.succeed());
        TableSchemas.dropTemporaryLinesTable(test, tableName.failed());
        TableSchemas.dropUploadsTable(test);
    }

    @Test
    public void updateFailedLineUploadProcess(){
        String failedLines = "(123)";
        uploadRepository.reportLines(tableName.failed(), failedLines);
        IUploadStatus oldStatus = uploadRepository.getStatus(processId);
        String failedChunk = "(245), (263)";

        uploadRepository.reportLines(tableName.failed(), failedChunk);
        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(oldStatus.getProcessId(), status.getProcessId());
        assertEquals(oldStatus.getFileName(), status.getFileName());
        assertEquals(oldStatus.getFailed() + 2, status.getFailed());
        assertEquals(oldStatus.getCompleted(), status.getCompleted());
    }

    @Test
    public void updateTotalLines(){
        Integer lines = 5;
        IUploadStatus oldStatus = uploadRepository.getStatus(processId);

        uploadRepository.updateTotalLines(processId, lines);
        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(oldStatus.getProcessId(), status.getProcessId());
        assertEquals(oldStatus.getFileName(), status.getFileName());
        assertEquals(oldStatus.getTotal() + lines, status.getTotal());
    }

    @Test
    public void getUploadProcess(){

        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(processId, status.getProcessId());
    }

    @Test
    public void createUploadProcess(){
        String fileName = "employess.csv";

        Integer processId = uploadRepository.create(fileName);

        assertNotNull(processId);
    }
}
