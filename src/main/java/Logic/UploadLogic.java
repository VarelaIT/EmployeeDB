package Logic;

import Persistence.*;
import UploadFileService.ManagerThread;

public class UploadLogic implements IUploadLogic{

    private final IUploadRepository uploadRepository;
    private String test = null;

    public UploadLogic(){
        this.uploadRepository = new UploadRepository();
    }

    public UploadLogic(String test){
        this.test = test;
        this.uploadRepository = new UploadRepository(test);
    }

    @Override
    public Integer start(String fileName, String location) {
        Integer processId = uploadRepository.create(fileName);

        if (processId == null)
            return  null;

        ITableNameBuilder tableNames = new TableNameBuilder(processId);

        if (!TableSchemas.createTemporaryLinesTable(test, tableNames.succeed())) {
            TableSchemas.dropTemporaryLinesTable(test, tableNames.succeed());
            TableSchemas.createTemporaryLinesTable(test, tableNames.succeed());
        }

        if (!TableSchemas.createTemporaryLinesTable(test, tableNames.failed())) {
            TableSchemas.dropTemporaryLinesTable(test, tableNames.failed());
            TableSchemas.createTemporaryLinesTable(test, tableNames.failed());
        }

        Thread fileProcess = new Thread (new ManagerThread(processId, location, test));
        fileProcess.start();
        return processId;
    }

    @Override
    public IUploadStatusResponse status(Integer processId) {
        if (processId == null)
            return  null;

        return new UploadStatusResponse(uploadRepository.getStatus(processId));
    }

    @Override
    public void clean(Integer processId) {
        ITableNameBuilder tableNames = new TableNameBuilder(processId);
        TableSchemas.dropTemporaryLinesTable(test, tableNames.succeed());
        TableSchemas.dropTemporaryLinesTable(test, tableNames.failed());
    }
}
