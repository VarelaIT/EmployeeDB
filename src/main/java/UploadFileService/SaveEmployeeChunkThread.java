package UploadFileService;

import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.IUploadRepository;
import Persistence.UploadRepository;

public class SaveEmployeeChunkThread implements Runnable{
    private final String chunk;
    private String test = null;

    SaveEmployeeChunkThread(String chunk, String test){
        this.chunk = chunk;
        this.test = test;
    }

    @Override
    public void run() {
        IUploadRepository uploadRepository = new UploadRepository(test);
        uploadRepository.employeesChunk(chunk);
    }
}
