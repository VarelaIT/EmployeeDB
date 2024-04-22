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
    public void getUploadProcess(){
        String fileName = "employess.csv";
        Integer processId = uploadRepository.create(fileName);

        IUploadStatus status = uploadRepository.get(processId);

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
