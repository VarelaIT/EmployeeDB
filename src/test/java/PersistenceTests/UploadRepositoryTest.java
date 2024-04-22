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
        uploadRepository = new UploadRepository(test);
    }

    @Test
    public void updateFailedLineUploadProcess(){
        String fileName = "employees.csv";
        Integer processId = uploadRepository.create(fileName);
        IUploadStatus oldStatus = uploadRepository.getStatus(processId);

        uploadRepository.updateFailedLine(processId);
        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(oldStatus.getProcessId(), status.getProcessId());
        assertEquals(oldStatus.getFileName(), status.getFileName());
        assertEquals(oldStatus.failed() + 1, status.failed());
        assertEquals(oldStatus.completed(), status.completed());
        assertNotEquals(oldStatus.getTimeStamp(), status.getTimeStamp());
    }

    @Test
    public void updateCompletedLineUploadProcess(){
        String fileName = "employees.csv";
        Integer lines = 5;
        Integer processId = uploadRepository.create(fileName);
        IUploadStatus oldStatus = uploadRepository.getStatus(processId);

        uploadRepository.updateCompletedLine(processId, lines);
        IUploadStatus status = uploadRepository.getStatus(processId);

        assertEquals(oldStatus.getProcessId(), status.getProcessId());
        assertEquals(oldStatus.getFileName(), status.getFileName());
        assertEquals(oldStatus.completed() + lines, status.completed());
        assertEquals(oldStatus.failed(), status.failed());
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
