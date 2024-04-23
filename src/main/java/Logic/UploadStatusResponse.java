package Logic;

import Entities.Upload;
import Persistence.IUploadStatus;

import java.sql.Timestamp;

public class UploadStatusResponse extends Upload implements IUploadStatusResponse{

    UploadStatusResponse(IUploadStatus status){

        this.processId = status.getProcessId();
        this.file = status.getFileName();
        this.completed = status.getCompleted();
        this.inCompletedLines = status.getFailed();
        this.total = status.getTotal();
        this.timeStamp = status.getTimeStamp();

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
