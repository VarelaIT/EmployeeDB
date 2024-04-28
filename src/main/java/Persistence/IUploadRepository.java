package Persistence;

public interface IUploadRepository {
    Integer create(String file);
    IUploadStatus getStatus(int id);
    Integer reportValidLines(String table, String chunk);
    void updateTotalLines(int processId, int lines);
    void insertFailedLines(int id, String invalidChunk);
}
