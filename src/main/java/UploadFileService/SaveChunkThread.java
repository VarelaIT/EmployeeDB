package UploadFileService;

import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;

public class SaveChunkThread implements Runnable{
    private final String chunk;
    private final int processId;
    private String test = null;

    SaveChunkThread(int id, String chunk, String test){
        this.processId = id;
        this.chunk = chunk;
        this.test = test;
    }

    @Override
    public void run() {
        IEmployeeRepository employeeRepository = new EmployeeRepository(test);
        employeeRepository.chunkData(processId, chunk);
    }
}
