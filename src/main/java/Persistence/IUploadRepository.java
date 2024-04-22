package Persistence;

public interface IUploadRepository {
    Integer create(String file);
    IUploadStatus getStatus(int id);
    void updateCompletedLine(int id);
    void updateFailedLine(int id);
}
