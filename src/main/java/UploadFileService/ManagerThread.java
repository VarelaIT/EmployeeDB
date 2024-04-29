package UploadFileService;

public class ManagerThread implements Runnable{
    private final String test;
    private final String filePath;
    private final int processId;

    public ManagerThread(int processId, String filePath, String test){
        this.processId = processId;
        this.filePath = filePath;
        this.test = test;
    }

    @Override
    public void run() {
        new FileUploadManager(processId, filePath, test);
    }
}
