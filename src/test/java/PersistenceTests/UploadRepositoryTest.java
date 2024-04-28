package PersistenceTests;

import Persistence.IUploadRepository;
import Persistence.IUploadStatus;
import Persistence.TableSchemas;
import Persistence.UploadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UploadRepositoryTest {

    private IUploadRepository uploadRepository;
    private String test = "test";

    @BeforeEach
    public void storageInitialization(){
        TableSchemas.dropUploadsTable(test);
        TableSchemas.createUploadsTable(test);
        TableSchemas.dropFailedLinesTable(test);
        TableSchemas.createFailedLinesTable(test);
        uploadRepository = new UploadRepository(test);
    }


    @Test
    public void updateFailedLineUploadProcess(){
        String fileName = "employees.csv";
        int failedLineNumber = 123;
        Integer processId = uploadRepository.create(fileName);
        IUploadStatus oldStatus = uploadRepository.getStatus(processId);
        String failedChunk = "(" + processId + ", " + failedLineNumber + ")";

        uploadRepository.insertFailedLines(processId, failedChunk);
        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(oldStatus.getProcessId(), status.getProcessId());
        assertEquals(oldStatus.getFileName(), status.getFileName());
        assertEquals(oldStatus.getFailed() + 1, status.getFailed());
        assertEquals(oldStatus.getCompleted(), status.getCompleted());
        assertNotEquals(oldStatus.getTimeStamp(), status.getTimeStamp());
    }

    @Test
    public void updateTotalLines(){
        String fileName = "employees.csv";
        Integer lines = 5;
        Integer processId = uploadRepository.create(fileName);
        IUploadStatus oldStatus = uploadRepository.getStatus(processId);

        uploadRepository.updateTotalLines(processId, lines);
        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(oldStatus.getProcessId(), status.getProcessId());
        assertEquals(oldStatus.getFileName(), status.getFileName());
        assertEquals(oldStatus.getTotal() + lines, status.getTotal());
        assertNotEquals(oldStatus.getTimeStamp(), status.getTimeStamp());
    }

    @Test
    public void getUploadProcess(){
        String fileName = "employess.csv";
        Integer processId = uploadRepository.create(fileName);

        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(processId, status.getProcessId());
        assertEquals(fileName, status.getFileName());
    }

    @Test
    public void createUploadProcess(){
        String fileName = "employess.csv";

        Integer processId = uploadRepository.create(fileName);

        assertNotNull(processId);
    }
}
