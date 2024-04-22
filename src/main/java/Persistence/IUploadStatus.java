package Persistence;

public interface IUploadStatus {
    int getProcessId();
    String getFileName();
    Integer completed();
    Integer failed();
    java.sql.Date getTimeStamp();
    boolean isDone();
}
