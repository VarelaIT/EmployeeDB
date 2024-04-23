package Persistence;

public interface IUploadRepository {
    Integer create(String file);
    IUploadStatus getStatus(int id);
    void updateCompletedLine(int processId, int lines);
    void updateFailedLine(int id);
}
