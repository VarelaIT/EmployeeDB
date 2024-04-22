package Persistence;

import Entities.Upload;

import java.sql.Timestamp;

public class UploadStatus extends Upload implements IUploadStatus {

    UploadStatus(int id, String fileName, int completed, int failed, Timestamp time){
        this.processId = id;
        this.file = fileName;
        this.completedLines = completed;
        this.inCompletedLines = failed;
        this.timeStamp = time;
    }
    @Override
    public int getProcessId() {
        return processId;
    }

    public String getFileName(){
        return file;
    }
    @Override
    public Integer completed() {
        return completedLines;
    }

    @Override
    public Integer failed() {
        return inCompletedLines;
    }

    @Override
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

}
