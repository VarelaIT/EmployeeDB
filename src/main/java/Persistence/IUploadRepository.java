package Persistence;

public interface IUploadRepository {
    int create(String file);
    IUploadStatus get(int id);
}
