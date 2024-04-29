package UploadFileService;

import Persistence.IUploadRepository;
import Persistence.UploadRepository;

public class ReportLinesThread implements Runnable{

    private final String invalidChunk;
    private final String table;
    private String test = null;

    ReportLinesThread(String table, String invalidChunk, String test){
        this.table = table;
        this.invalidChunk = invalidChunk;
        this.test = test;
    }

    @Override
    public void run() {
        IUploadRepository uploadRepository = new UploadRepository(test);
        uploadRepository.reportLines(table, invalidChunk);
    }
}
