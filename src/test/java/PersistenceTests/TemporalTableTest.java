package PersistenceTests;

import Persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TemporalTableTest {

    private String test = "test";

    @BeforeEach
    public void setupt(){
    }

    @Test
    public void createDeleteSucceedTemporaryTable(){
        int processId = 1;
        ITableNameBuilder tableName = new TableNameBuilder(processId);

        assertTrue(TableSchemas.createTemporaryLinesTable(test, tableName.succeed()));
        assertTrue(TableSchemas.dropTemporaryLinesTable(test, tableName.succeed()));

    }

    @Test
    public void insertIntoTemporalTable(){
        int processId = 1;
        String succedChunk = """
            (1),
            (2),
            (3),
            (4)
        """;
        ITableNameBuilder tableName = new TableNameBuilder(processId);
        TableSchemas.createTemporaryLinesTable(test, tableName.succeed());
        IUploadRepository uploadRepository = new UploadRepository(test);

        Integer inserted = uploadRepository.reportValidLines(tableName.succeed(), succedChunk);

        assertNotNull(inserted);
        assertEquals(4, inserted);
        assertTrue(TableSchemas.dropTemporaryLinesTable(test, tableName.succeed()));
    }

}
