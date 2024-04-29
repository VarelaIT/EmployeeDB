package Persistence;

public interface IUploadRepository {
    Integer create(String file);
    IUploadStatus getStatus(int id);
    Integer reportLines(String table, String chunk);
    Integer countLines(String table);
    void updateTotalLines(int processId, int lines);
    void insertFailedLines(int id, String invalidChunk);
}
