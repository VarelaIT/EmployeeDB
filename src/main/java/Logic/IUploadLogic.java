package Logic;

public interface IUploadLogic {
    Integer start(String fileName, String location);
    IUploadStatusResponse status(Integer processId);
}
