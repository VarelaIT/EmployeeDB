package FileService;

import Persistence.IUploadRepository;
import Persistence.UploadRepository;

public class ReportFailedThread implements Runnable{

    private final int lineNuber;
    private final int processId;
    private String test = null;

    ReportFailedThread(int id, int lineNumber){
        this.processId = id;
        this.lineNuber = lineNumber;
    }

    ReportFailedThread(int id, int lineNumber, String test){
        this.processId = id;
        this.lineNuber = lineNumber;
        this.test = test;
    }

    @Override
    public void run() {
        IUploadRepository uploadRepository = new UploadRepository(test);
        uploadRepository.updateFailedLine(processId, lineNuber);
    }
}
