package FileServiceTest;

import Persistence.EmployeeRepository;
import Persistence.IEmployeeRepository;
import Persistence.IUploadRepository;
import Persistence.UploadRepository;

public class SaveChunk implements Runnable{
    private final String chunk;
    private String test = null;
    private final int processId;

    SaveChunk(int id, String chunk){
        this.chunk = chunk;
        this.processId = id;
    }

    SaveChunk(int id, String chunk, String test){
        this.chunk = chunk;
        this.test = test;
        this.processId = id;
    }

    @Override
    public void run() {
        IEmployeeRepository employeeRepository = new EmployeeRepository(test);
        IUploadRepository uploadRepository = new UploadRepository(test);

        int affectedRows = employeeRepository.chunkData(chunk);
        uploadRepository.updateCompletedLine(processId, affectedRows);
    }
}
