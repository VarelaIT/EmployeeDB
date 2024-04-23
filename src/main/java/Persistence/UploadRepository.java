package Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UploadRepository implements IUploadRepository{
    private String creationQuery = """
        INSERT INTO uploads (file) VALUES (?) RETURNING id
    """;
    private String selectionQuery = """
        SELECT id, file, completed, failed, modified FROM uploads WHERE id = ?
    """;
    private static final Logger logger = LogManager.getLogger("regular");
    private String test = null;

    public UploadRepository(){}
    public UploadRepository(String test){
        this.test =  test;
    }
    @Override
    public Integer create(String file) {
        Integer value = null;
        try (Connection conn = PersistenceConnectivity.get(test)) {
            PreparedStatement st = conn.prepareStatement(creationQuery);
            st.setString(1, file);
            ResultSet result = st.executeQuery();

            if (result.next())
                value = result.getInt("id");

            result.close();
            st.close();

            return value;
        } catch (Exception e){
            logger.error("While creating an upload register\n\t" + e.getMessage());
        }

        return value;
    }

    @Override
    public IUploadStatus getStatus(int id) {
        IUploadStatus status = null;
        try (Connection conn = PersistenceConnectivity.get(test)) {
            PreparedStatement st = conn.prepareStatement(selectionQuery);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();

            if (result.next()) {
                status = new UploadStatus(
                    result.getInt("id"),
                    result.getString("file"),
                    result.getInt("completed"),
                    result.getInt("failed"),
                    result.getTimestamp("modified")
                );
            }

            result.close();
            st.close();

            return status;
        } catch (Exception e){
            logger.error("While reading an upload register\n\t" + e.getMessage());
        }

        return null;
    }

    private String updateFailedLineQuery = """
        UPDATE uploads SET failed = failed + 1, modified = NOW() WHERE id = ?
    """;
    @Override
    public void updateFailedLine(int id) {

        try (Connection conn = PersistenceConnectivity.get(test)) {
            PreparedStatement st = conn.prepareStatement(updateFailedLineQuery);
            st.setInt(1, id);
            Integer afectedRows = st.executeUpdate();
            st.close();
        } catch (Exception e){
            logger.error("While updating failed lines on uploaded register\n\t" + e.getMessage());
        }
    }

    private String updateCompletedLineQuery = """
        UPDATE uploads SET completed = completed + ?, modified = NOW() WHERE id = ?
    """;
    @Override
    public void updateCompletedLine(int ProcessId, int lines) {

        try (Connection conn = PersistenceConnectivity.get(test)) {
            PreparedStatement st = conn.prepareStatement(updateCompletedLineQuery);
            st.setInt(1, lines);
            st.setInt(2, ProcessId);
            Integer afectedRows = st.executeUpdate();
            st.close();
        } catch (Exception e){
            logger.error("While updating completed lines on uploaded register\n\t" + e.getMessage());
        }
    }
}
