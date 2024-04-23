package Persistence;

import java.sql.Timestamp;

public interface IUploadStatus {
    int getProcessId();
    String getFileName();
    Integer getCompleted();
    Integer getFailed();
    Integer getTotal();
    Timestamp getTimeStamp();
}
