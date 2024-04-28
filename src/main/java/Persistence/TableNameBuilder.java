package Persistence;

public class TableNameBuilder implements ITableNameBuilder{

    private final int processId;

    public TableNameBuilder(int processId) {
        this.processId = processId;
    }

    @Override
    public String succeed() {
        return TempTableName.SUCCED.toString() + processId;
    }

    @Override
    public String failed() {
        return TempTableName.FAILED.toString() + processId;
    }
}
