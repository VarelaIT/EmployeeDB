package Persistence;

public interface IUploadRepository {
    Integer create(String file);
    IUploadStatus get(int id);
}
