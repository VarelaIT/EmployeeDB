package UploadFileService;

import Persistence.IUploadRepository;
import Persistence.UploadRepository;

public class ReportFailedThread implements Runnable{

    private final String invalidChunk;
    private final int processId;
    private String test = null;

    ReportFailedThread(int id, String invalidChunk, String test){
        this.processId = id;
        this.invalidChunk = invalidChunk;
        this.test = test;
    }

    @Override
    public void run() {
        IUploadRepository uploadRepository = new UploadRepository(test);
        uploadRepository.insertFailedLines(processId, invalidChunk);
    }
}
