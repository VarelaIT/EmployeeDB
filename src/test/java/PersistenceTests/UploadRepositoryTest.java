package PersistenceTests;

import Persistence.IUploadRepository;
import Persistence.TableSchemas;
import Persistence.UploadRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void createUploadProcess(){
        String fileName = "employess.csv";

        Integer processId = uploadRepository.create(fileName);

        assertNotNull(processId);
    }
}
