package PersistenceTests;

import Persistence.ITableNameBuilder;
import Persistence.TableNameBuilder;
import Persistence.TableSchemas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
