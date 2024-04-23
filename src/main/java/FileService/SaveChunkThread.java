package FileService;

import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.IUploadRepository;
import Persistence.UploadRepository;

public class SaveChunkThread implements Runnable{
    private final String chunk;
    private final int processId;
    private String test = null;

    SaveChunkThread(int id, String chunk){
        this.processId = id;
        this.chunk = chunk;
    }

    SaveChunkThread(int id, String chunk, String test){
        this.processId = id;
        this.chunk = chunk;
        this.test = test;
    }

    @Override
    public void run() {
        IEmployeeRepository employeeRepository = new EmployeeRepository(test);
        IUploadRepository uploadRepository = new UploadRepository(test);

        int affectedRows = employeeRepository.chunkData(chunk);
        uploadRepository.updateCompletedLine(processId, affectedRows);
    }
}
