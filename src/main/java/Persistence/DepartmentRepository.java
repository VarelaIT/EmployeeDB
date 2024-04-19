package Persistence;

import Entities.IDepartment;
import Entities.IPersistedDepartment;
import Entities.PersistedDepartment;
import Persistence.JDBC.DBConn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository implements IDepartmentRepository{
    public String seletAllQuery = "SELECT id, name, description FROM departments ORDER BY id LIMIT ? OFFSET ?";
    public String seletOneQuery = "SELECT id, name, description FROM departments WHERE id = ?";
    public String deleteOneQuery = "DELETE FROM departments WHERE id = ?";
    public String insertionQuery = """
       INSERT INTO departments (name, description)
       VALUES ( ?, ?)
       RETURNING id, name, description
    """;
    public String updateQuery = """
       UPDATE departments SET name = ?, description = ?
       Where id = ?
       RETURNING id, name, description
    """;
    private String test = null;

    private static final Logger logger = LogManager.getLogger("regular");

    public DepartmentRepository(){
    }

    public DepartmentRepository(String test){
        this.test = test;
    }

    public  IPersistedDepartment save(IDepartment newDepartment) {
        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement st = conn.prepareStatement(insertionQuery);
            st.setString(1, newDepartment.getName());
            st.setString(2, newDepartment.getDescription());

            ResultSet result = st.executeQuery();

            result.next();
            int id = result.getInt("id");
            String name = result.getString("name");
            String description = result.getString("description");

            result.close();
            st.close();

            return new PersistedDepartment(id, name, description);
        } catch (Exception e){
            logger.error("While persisting a department.\n\t" + e.getMessage());
        }

        return null;
    }

    public  IPersistedDepartment update(int id, IDepartment department) {
        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement st = conn.prepareStatement(updateQuery);
            st.setString(1, department.getName());
            st.setString(2, department.getDescription());
            st.setInt(3, id);

            ResultSet result = st.executeQuery();
            IPersistedDepartment persistedDepartment;

            result.next();
            persistedDepartment = new PersistedDepartment(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("description")
            );

            result.close();
            st.close();

            return persistedDepartment;
        } catch (Exception e){
            logger.error("While updating a department.\n\t" + e.getMessage());
        }

        return null;
    }

    public IPersistedDepartment get(int id) {
        IPersistedDepartment persistedDepartment = null;

        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement stm = conn.prepareStatement(seletOneQuery);
            stm.setInt(1, id);
            ResultSet result = stm.executeQuery();

            if (result.next()) {
                persistedDepartment = new PersistedDepartment(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description")
                );
            }

            result.close();
            stm.close();
            return persistedDepartment;
        } catch (Exception e) {
            logger.error("While withdrawing a department:\n\t" + e.getMessage());
        }

        return null;
    }

    @Override
    public List<IPersistedDepartment> getAll() {
        return getAll(25, 0);
    }

    @Override
    public List<IPersistedDepartment> getAll(int size, int page) {
        List<IPersistedDepartment> response = new ArrayList<IPersistedDepartment>();

        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement stm = conn.prepareStatement(seletAllQuery);
            stm.setInt(1, size);
            stm.setInt(2, page);
            ResultSet result = stm.executeQuery();
            while(result.next()) {
                response.add(new PersistedDepartment(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("description")
                ));
            }

            result.close();
            stm.close();
            return response;
        } catch (Exception e) {
            logger.error("While withdrawing the departments:\n\t" + e.getMessage());
        }

        return null;
    }

    @Override
    public IPersistedDepartment delete(int id){
        IPersistedDepartment department = get(id);

        if (department == null)
            return null;

        try (Connection conn = PersistenceConnectivity.get(test)){
            PreparedStatement st = conn.prepareStatement(deleteOneQuery);
            st.setInt(1, department.getId());
            int affectedRows = st.executeUpdate();
            st.close();

            if (affectedRows != 1)
                return null;
        } catch (Exception e) {
            logger.error("While deleting a departments:\n\t" + e.getMessage());
        }

        return department;
    }

}
