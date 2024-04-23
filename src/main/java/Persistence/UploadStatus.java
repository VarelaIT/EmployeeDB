package Persistence;

import Entities.Upload;

import java.sql.Timestamp;

public class UploadStatus extends Upload implements IUploadStatus {

    UploadStatus(int id, String fileName, int completed, int failed, int total, Timestamp time){
        this.processId = id;
        this.file = fileName;
        this.completed = completed;
        this.total = total;
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
    public Integer getCompleted() {
        return completed;
    }

    @Override
    public Integer getFailed() {
        return inCompletedLines;
    }

    @Override
    public Integer getTotal() {
        return total;
    }

    @Override
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

}
