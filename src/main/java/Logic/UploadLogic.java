package Logic;

import Persistence.IUploadRepository;
import Persistence.UploadRepository;
import UploadFileService.ManagerThread;

public class UploadLogic implements IUploadLogic{

    private final IUploadRepository uploadRepository;
    private String test = null;

    UploadLogic(){
        this.uploadRepository = new UploadRepository();
    }

    public UploadLogic(String test){
        this.test = test;
        this.uploadRepository = new UploadRepository(test);
    }

    @Override
    public Integer start(String fileName, String location) {
        Integer processId = uploadRepository.create(fileName);
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
}
