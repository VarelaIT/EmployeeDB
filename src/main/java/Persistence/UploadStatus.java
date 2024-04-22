package Persistence;

import Entities.Upload;

import java.sql.Date;

public class UploadStatus extends Upload implements IUploadStatus {
    @Override
    public int getProcessId() {
        return processId;
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
    public Date getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean isDone() {
        return done;
    }
}
