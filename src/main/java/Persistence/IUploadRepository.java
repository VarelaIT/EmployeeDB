package Persistence;

public interface IUploadRepository {
    Integer create(String file);
    IUploadStatus getStatus(int id);
    void updateCompletedLines(int processId, int lines);
    void updateTotalLines(int processId, int lines);
    void updateFailedLine(int id, int line);
}
