package Persistence;

import java.sql.Timestamp;

public interface IUploadStatus {
    int getProcessId();
    String getFileName();
    Integer completed();
    Integer failed();
    Timestamp getTimeStamp();
    boolean isDone();
}
