package UploadFileService;

import static UploadFileService.ReadFile.manage;

public class ManagerThread implements Runnable{
    private final String test;
    private final String filePath;
    private final int processId;

    ManagerThread(int processId, String filePath, String test){
        this.processId = processId;
        this.filePath = filePath;
        this.test = test;
    }

    @Override
    public void run() {
        manage(processId, filePath, test);
    }
}
