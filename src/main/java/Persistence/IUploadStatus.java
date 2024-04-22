package Persistence;

public interface IUploadStatus {
    int getProcessId();
    Integer completed();
    Integer failed();
    java.sql.Date getTimeStamp();
    boolean isDone();
}
