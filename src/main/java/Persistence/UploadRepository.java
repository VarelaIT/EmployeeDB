package Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UploadRepository implements IUploadRepository{
    private String creationQuery = """
        INSERT INTO uploads (file) VALUES (?) RETURNING id
    """;
    private String selectionQuery = """
        SELECT id, file, completed, failed, total, modified FROM uploads WHERE id = ?
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
        String updateQuery = """
            UPDATE uploads SET
            failed = (SELECT COUNT(*) FROM failed_lines WHERE id = ?),
            modified = NOW() WHERE id = ?
        """;

        try (Connection conn = PersistenceConnectivity.get(test)) {
            PreparedStatement st = conn.prepareStatement(updateQuery);
            st.setInt(1, id);
            st.setInt(2, id);
            st.executeUpdate();

            st = conn.prepareStatement(selectionQuery);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();

            if (result.next()) {
                status = new UploadStatus(
                    result.getInt("id"),
                    result.getString("file"),
                    result.getInt("completed"),
                    result.getInt("failed"),
                    result.getInt("total"),
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

    @Override
    public void insertFailedLines(int id, String invalidChunk) {
        String reportFailedLineQuery = "INSERT INTO failed_lines (process_id, line) VALUES " + invalidChunk;

        try (Connection conn = PersistenceConnectivity.get(test)) {
            Statement st = conn.createStatement();
            st.executeUpdate(reportFailedLineQuery);
            st.close();
        } catch (Exception e){
            logger.error("While inserting failed lines on uploaded register\n\t" + e.getMessage());
        }
    }

    private String updateCompletedLineQuery = """
        UPDATE uploads SET completed = completed + ?, modified = NOW() WHERE id = ?
    """;
    @Override
    public void updateCompletedLines(int processId, int lines) {

        try (Connection conn = PersistenceConnectivity.get(test)) {
            PreparedStatement st = conn.prepareStatement(updateCompletedLineQuery);
            st.setInt(1, lines);
            st.setInt(2, processId);
            st.executeUpdate();
            st.close();
        } catch (Exception e){
            logger.error("While updating completed lines on uploaded register\n\t" + e.getMessage());
        }
    }

    private String updateTotalLinesQuery = """
        UPDATE uploads SET total = ?, modified = NOW() WHERE id = ?
    """;
    @Override
    public void updateTotalLines(int processId, int lines) {
        try (Connection conn = PersistenceConnectivity.get(test)) {
            PreparedStatement st = conn.prepareStatement(updateTotalLinesQuery);
            st.setInt(1, lines);
            st.setInt(2, processId);
            Integer afectedRows = st.executeUpdate();
            st.close();
        } catch (Exception e){
            logger.error("While setting total lines on uploaded register\n\t" + e.getMessage());
        }
    }
}
